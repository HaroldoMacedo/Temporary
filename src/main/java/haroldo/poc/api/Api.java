package haroldo.poc.api;

public interface Api {

    String getUri();

    String getDefaultMessage();

    long getAvgResponseTimeMs();

    ApiResponse getApiResponse();
}
