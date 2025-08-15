package haroldo.poc.api;

public class DefaultApi implements Api {
    private final String uri;
    private final String defaultMessage;
    private final long avgResponseTimeMs;
    private final ApiResponse apiResponse;

    public DefaultApi(String uri) {
        this.uri = uri;
        this.avgResponseTimeMs = 100;
        this.defaultMessage = "Hello World!";
        apiResponse = new ApiResponse(defaultMessage,  avgResponseTimeMs);
    }

    public DefaultApi(String uri, String defaultMessage, long avgResponseTimeMs) {
        this.uri = uri;
        this.avgResponseTimeMs = avgResponseTimeMs;
        this.defaultMessage = defaultMessage;
        apiResponse = new ApiResponse(defaultMessage,  avgResponseTimeMs);
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public long getAvgResponseTimeMs() {
        return avgResponseTimeMs;
    }

    @Override
    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
