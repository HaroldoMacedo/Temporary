package haroldo.poc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class TestUtils {
    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
        }
    }

    public static boolean isPortOpen(int port) {
        return isPortOpen("localhost", port);
    }

    public static boolean isPortOpen(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(host, port), 50);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isHttpPortOpen(int port) {
        return isHttpPortOpen("localhost", port);
    }

    public static boolean isHttpPortOpen(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 50);

            // Send a basic HTTP GET request
            OutputStream os = socket.getOutputStream();
            String request = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
            os.write(request.getBytes());
            os.flush();

            return true;
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out.");
            return false;
        } catch (IOException e) {
            System.out.println("Unable to connect: " + e.getMessage());
            return false;
        }
    }

    public static boolean isEndpointResponding(int port, String uri) {
        try {
            URL url = new URL("http://localhost:" + port + uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);

            int status = connection.getResponseCode();
            connection.disconnect();

            return status == 200;
        } catch (Exception e) {
            return false;
        }
    }

}
