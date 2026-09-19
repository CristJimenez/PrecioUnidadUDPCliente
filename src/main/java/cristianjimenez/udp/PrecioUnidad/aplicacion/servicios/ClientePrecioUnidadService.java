package cristianjimenez.udp.PrecioUnidad.aplicacion.servicios;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ConectarCommand;
import cristianjimenez.udp.PrecioUnidad.aplicacion.excepciones.ClienteRedException;
import cristianjimenez.udp.PrecioUnidad.aplicacion.mapper.ClienteMapper;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.CalcularImcInputPort;
import cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada.GestionarConexionInputPort;
import cristianjimenez.udp.PrecioUnidad.dominio.enums.EstadoConexion;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.EventoCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.ResultadoPrecioUnidad;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.ClienteUdpPort;
import cristianjimenez.udp.PrecioUnidad.dominio.puertos.salida.PuertoNotificacionCliente;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.DestinoServidor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public final class ClientePrecioUnidadService implements GestionarConexionInputPort, CalcularImcInputPort {
    private static final Logger LOG = LoggerFactory.getLogger(ClientePrecioUnidadService.class);
    private static final String LOG_ERROR_OPERACION = "Falló la operación UDP {}";
    private static final String OPERACION_CONECTAR = "conectar";
    private static final String OPERACION_DESCONECTAR = "desconectar";
    private static final String OPERACION_CALCULAR = "calcular";
    @NonNull private final ClienteUdpPort clienteUdp;
    @NonNull private final PuertoNotificacionCliente notificador;
    @NonNull private final ClienteMapper mapper;
    @NonNull private final Executor executor;

    @Override
    public CompletableFuture<Void> conectar(final ConectarCommand comando) {
        final DestinoServidor destino = mapper.toDestino(comando);
        notificador.notificarEstado(EstadoConexion.CONECTANDO, destino.endpoint());
        return CompletableFuture.runAsync(() -> {
            try {
                clienteUdp.conectar(destino);
                notificador.notificarEstado(EstadoConexion.CONECTADO, destino.endpoint());
                notificador.notificarEvento(new EventoCliente("CONEXIÓN", "Conectado a " + destino.endpoint()));
            } catch (final ClienteRedException excepcion) {
                LOG.error(LOG_ERROR_OPERACION, OPERACION_CONECTAR, excepcion);
                notificador.notificarEstado(EstadoConexion.DESCONECTADO, destino.endpoint());
                notificador.notificarError(excepcion.getMessage());
                throw new CompletionException(excepcion);
            }
        }, executor);
    }

    @Override
    public CompletableFuture<Void> desconectar() {
        return CompletableFuture.runAsync(() -> {
            try {
                clienteUdp.desconectar();
                notificador.notificarEstado(EstadoConexion.DESCONECTADO, "");
                notificador.notificarEvento(new EventoCliente("CONEXIÓN", "Cliente desconectado."));
            } catch (final ClienteRedException excepcion) {
                LOG.error(LOG_ERROR_OPERACION, OPERACION_DESCONECTAR, excepcion);
                notificador.notificarError(excepcion.getMessage());
                throw new CompletionException(excepcion);
            }
        }, executor);
    }

    @Override
    public CompletableFuture<ResultadoPrecioUnidad> calcular(final CalcularPrecioUnidadCommand comando) {
        final var datos = mapper.toDatos(comando);
        return CompletableFuture.supplyAsync(() -> {
            try {
                final ResultadoPrecioUnidad resultado = clienteUdp.solicitarCalculo(datos);
                notificador.notificarResultado(resultado);
                return resultado;
            } catch (final ClienteRedException excepcion) {
                LOG.error(LOG_ERROR_OPERACION, OPERACION_CALCULAR, excepcion);
                notificador.notificarError(excepcion.getMessage());
                throw new CompletionException(excepcion);
            }
        }, executor);
    }

    @Override
    public boolean estaConectado() {
        return clienteUdp.estaConectado();
    }
}
