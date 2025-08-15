package haroldo.poc;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import haroldo.poc.api.ApiResponse;
import haroldo.poc.api.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultHttpHandler implements HttpHandler {
    final ApiResponse apiResponse;
    private final long timeBetweenRequestReceivedNanoSec;
    private long lastRequestReceivedNanoSec = 0;

    private static final int COUNTPERPRINT = 100;

    public DefaultHttpHandler(int maxThroughPutPerSecond, ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
        long nanoSecPerSecond = 1000000000;
        this.timeBetweenRequestReceivedNanoSec = nanoSecPerSecond / maxThroughPutPerSecond;
    }

    private int count = 0;

    @Override
    public void handle(HttpExchange exchange) {
        if (++count % COUNTPERPRINT == 0)
            System.out.println(now() + " - " + count + ": Request '" + exchange.getRequestMethod() + " " + exchange.getRequestURI() + "' received!");

        switch (exchange.getRequestMethod()) {
            case "GET":
                respondRequest(exchange, apiResponse.getGetResponse());
                break;
            case "POST":
                respondRequest(exchange, apiResponse.getPostResponse());
                break;
            case "PUT":
                respondRequest(exchange, apiResponse.getPutResponse());
                break;
            case "DELETE":
                respondRequest(exchange, apiResponse.getDeleteResponse());
                break;
            default:
                System.err.println("Method '" + exchange.getRequestMethod() + "' is not implemented.");
        }
        if (count % COUNTPERPRINT == 0)
            System.out.println(now() + " - " + count + ": Responded to '" + exchange.getRequestMethod() + " " + exchange.getRequestURI() + "'");
    }

    void respondRequest(HttpExchange exchange, Response response) {
        try {
            throttle();
            OutputStream os = exchange.getResponseBody();
            Thread.sleep(response.getLatencyMs());
            byte[] message = response.getMessage().getBytes();
            exchange.sendResponseHeaders(200, message.length);
            os.write(message);
            os.close();

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void throttle() {
        long waitNanoSec = (lastRequestReceivedNanoSec + timeBetweenRequestReceivedNanoSec) - System.nanoTime();
        lastRequestReceivedNanoSec = System.nanoTime();

        if (waitNanoSec <= 0)
            return;

        try {
            if (waitNanoSec < 1000000)
                Thread.sleep(0, (int)waitNanoSec);
            else
                Thread.sleep(waitNanoSec / 1000000, (int)waitNanoSec % 1000000);
        } catch (InterruptedException e) {}

        lastRequestReceivedNanoSec = System.nanoTime();
    }

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss.SSS");
    private String now() {
        return LocalDateTime.now().format(dateFormat);
    }
}
