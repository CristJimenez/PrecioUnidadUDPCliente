package cristianjimenez.udp.PrecioUnidad.dominio.vo;

import cristianjimenez.udp.PrecioUnidad.dominio.excepciones.CantidadIncorrectaException;

public record Cantidad(int valor) {
    public Cantidad {
        if (valor <= 0) {
            throw new CantidadIncorrectaException("La cantidad debe ser un número finito mayor que cero.");
        }
    }
}
