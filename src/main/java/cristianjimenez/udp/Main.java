package cristianjimenez.udp;

import cristianjimenez.udp.PrecioUnidad.adaptadores.notificacion.AdaptadorNotificacionCliente;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.AdaptadorClienteUdp;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.CanalUdp;
import cristianjimenez.udp.PrecioUnidad.adaptadores.red.ProtocoloUdpMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.ClienteMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.servicios.ClientePrecioUnidadService;
import cristianjimenez.udp.PrecioUnidad.entrypoint.gui.ClienteFrame;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Main {
    public static void main(final String[] args) {
        final AdaptadorNotificacionCliente notificador = new AdaptadorNotificacionCliente();
        final AdaptadorClienteUdp udp = new AdaptadorClienteUdp(new CanalUdp(3000), new ProtocoloUdpMapper());
        final ExecutorService executor = Executors.newSingleThreadExecutor(tarea -> {
            final Thread hilo = new Thread(tarea, "cliente-udp");
            hilo.setDaemon(true);
            return hilo;
        });
        final ClientePrecioUnidadService servicio = new ClientePrecioUnidadService(
                udp, notificador, new ClienteMapper(), executor);
        SwingUtilities.invokeLater(() -> {
            final ClienteFrame frame = new ClienteFrame(servicio, servicio);
            notificador.registrar(frame);
            frame.setVisible(true);
        });
    }
}
