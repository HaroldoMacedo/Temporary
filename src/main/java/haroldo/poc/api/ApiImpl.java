package haroldo.poc.api;

public class ApiImpl implements Api {
    private final String uri;
    private final long avgResponseTimeMs;

    public ApiImpl(String uri, long avgResponseTimeMs) {
        this.uri = uri;
        this.avgResponseTimeMs = avgResponseTimeMs;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public long getAvgResponseTimeMs() {   //  TODO: Should this info be here?
        return avgResponseTimeMs;
    }
}
