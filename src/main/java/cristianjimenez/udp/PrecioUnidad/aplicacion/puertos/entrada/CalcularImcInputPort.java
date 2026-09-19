package cristianjimenez.udp.PrecioUnidad.aplicacion.puertos.entrada;

import cristianjimenez.udp.PrecioUnidad.aplicacion.dto.CalcularPrecioUnidadCommand;
import cristianjimenez.udp.PrecioUnidad.dominio.modelos.ResultadoPrecioUnidad;

import java.util.concurrent.CompletableFuture;

public interface CalcularImcInputPort {
    CompletableFuture<ResultadoPrecioUnidad> calcular(CalcularPrecioUnidadCommand comando);
}
