package cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida;

import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoConexion;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.ResultadoPrecioUnidad;

public interface PuertoNotificacionCliente {
    void notificarEstado(EstadoConexion estado, String endpoint);

    void notificarEvento(EventoCliente evento);

    void notificarResultado(ResultadoPrecioUnidad resultado);

    void notificarError(String mensaje);
}
