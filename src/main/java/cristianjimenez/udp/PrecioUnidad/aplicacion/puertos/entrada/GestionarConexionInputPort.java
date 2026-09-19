package cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.ConectarCommand;

import java.util.concurrent.CompletableFuture;

public interface GestionarConexionInputPort {
    CompletableFuture<Void> conectar(ConectarCommand comando);

    CompletableFuture<Void> desconectar();

    boolean estaConectado();
}
