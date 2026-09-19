package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.RespuestaServidorException;

public record ResultadoPrecioUnidad (double precioUnidad) {
    public ResultadoPrecioUnidad {
        if (!Double.isFinite(precioUnidad) || Double.isNaN(precioUnidad)) {
            throw new RespuestaServidorException("El resultado recibido está incompleto o es inválido.");
        }
    }
}
