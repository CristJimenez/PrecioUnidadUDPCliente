package cristianjimenez.udp.PrecioUnidad.adaptadores.notificacion;

import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoConexion;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.ResultadoPrecioUnidad;

public interface ObservadorCliente {
    void onEstado(EstadoConexion estado, String endpoint);

    void onEvento(EventoCliente evento);

    void onResultado(ResultadoPrecioUnidad resultado);

    void onError(String mensaje);
}
