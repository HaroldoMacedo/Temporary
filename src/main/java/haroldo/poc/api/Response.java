package haroldo.poc.api;

public class Response {
    private final String message;
    private final long latencyMs;

    public Response(String message, long latencyMs) {
        this.message = message;
        this.latencyMs = latencyMs;
    }

    public String getMessage() {
        return message;
    }

    public long getLatencyMs() {
        return latencyMs;
    }
}
