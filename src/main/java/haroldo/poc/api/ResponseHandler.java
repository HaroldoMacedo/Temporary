package haroldo.poc.api;

public class ResponseHandler {

    private final Response response;

    public ResponseHandler(Response response) {
        this.response = response;
    }
    public Response getNextResponse() {
        return response;
    }
}
