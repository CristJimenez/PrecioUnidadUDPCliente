package cristianjimenez.udp.PrecioUnidad.dominio.modelos;

import cristianjimenez.udp.PrecioUnidad.dominio.vo.Cantidad;
import cristianjimenez.udp.PrecioUnidad.dominio.vo.Precio;

import java.util.Objects;

public record DatosPrecioUnidad(Precio precio, Cantidad cantidad) {
    public DatosPrecioUnidad {
        Objects.requireNonNull(precio, "El precio es obligatorio.");
        Objects.requireNonNull(cantidad, "La cantidad es obligatoria.");
    }
}
