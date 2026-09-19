package cristianjimenez.udp.PrecioUnidad.adaptadores.red;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.RespuestaServidorException;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.DatosPrecioUnidad;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.ResultadoPrecioUnidad;

import java.util.Locale;
import java.util.Objects;

public final class ProtocoloUdpMapper {
    public static final String CONECTAR = "CONECTAR";
    public static final String DESCONECTAR = "DESCONECTAR";

    public String serializarCalculo(final DatosPrecioUnidad datos) {
        Objects.requireNonNull(datos, "Los datos del precio unitario son obligatorios.");
        return String.format(Locale.US, "CALCULAR;%.2f;%d", datos.precio().valor(), datos.cantidad().valor());
    }

    public void validarConexion(final String respuesta) {
        if (Objects.isNull(respuesta) || !respuesta.startsWith("CONECTADO_OK;")) {
            throw new RespuestaServidorException("Respuesta de conexión inesperada: " + respuesta);
        }
    }

    public ResultadoPrecioUnidad parsearCalculo(final String respuesta) {
        if (Objects.isNull(respuesta) || respuesta.isBlank()) {
            throw new RespuestaServidorException("El servidor envió una respuesta vacía.");
        }
        if (respuesta.startsWith("ERROR;")) {
            throw new RespuestaServidorException("Error del servidor: " + respuesta.substring(6));
        }
        final String[] partes = respuesta.split(";", -1);
        if (partes.length != 2 || !"OK_CALCULO".equals(partes[0])) {
            throw new RespuestaServidorException("Respuesta de cálculo no estructurada: " + respuesta);
        }
        try {
            final double precioUnidad = Double.parseDouble(partes[1].replace(',', '.'));
            return new ResultadoPrecioUnidad(precioUnidad);
        } catch (final NumberFormatException excepcion) {
            throw new RespuestaServidorException("El precio unitario recibido no es numérico.");
        }
    }
}
