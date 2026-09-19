package cristianjimenez.udp.PrecioUnidad.adaptadores.red.util;

import lombok.experimental.UtilityClass;

import java.net.*;
import java.util.Enumeration;

@UtilityClass
public class RedUtil {
    public static String obtenerIpLocal() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                final NetworkInterface interfaz = interfaces.nextElement();
                if (interfaz.isLoopback() || !interfaz.isUp()) {
                    continue;
                }
                final Enumeration<InetAddress> direcciones = interfaz.getInetAddresses();
                while (direcciones.hasMoreElements()) {
                    final InetAddress direccion = direcciones.nextElement();
                    if (direccion instanceof Inet4Address && !direccion.isLoopbackAddress()) {
                        return direccion.getHostAddress();
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (final SocketException | UnknownHostException excepcion) {
            return "127.0.0.1";
        }
    }
}
