package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.PrecioIncorrectoException;

public record Precio(double valor) {
    public Precio {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new PrecioIncorrectoException("El precio debe ser un número finito mayor que cero.");
        }
    }
}
