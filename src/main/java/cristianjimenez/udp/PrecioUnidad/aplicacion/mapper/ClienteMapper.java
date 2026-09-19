package cristianjimenez.udp.PrecioUnidad.aplicacion.mapper;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ConectarCommand;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.DatosPrecioUnidad;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Cantidad;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.DestinoServidor;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Precio;

import java.util.Objects;

public final class ClienteMapper {
    public DestinoServidor toDestino(final ConectarCommand comando) {
        Objects.requireNonNull(comando, "El comando de conexión es obligatorio.");
        return new DestinoServidor(comando.host(), comando.puerto());
    }

    public DatosPrecioUnidad toDatos(final CalcularPrecioUnidadCommand comando) {
        Objects.requireNonNull(comando, "El comando de cálculo es obligatorio.");
        return new DatosPrecioUnidad(new Precio(comando.precio()), new Cantidad(comando.cantidad()));
    }
}
