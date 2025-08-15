package haroldo.poc.api;


public class ApiResponse {

    static final int GET = 0;
    static final int POST = 1;
    static final int PUT = 2;
    static final int DELETE = 3;

    final ResponseHandler responseHandler;

    public ApiResponse(String message, long latencyMs) {
        this.responseHandler = new DefaultResponseHandler(new Response(message, latencyMs));
    }

    public ApiResponse(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Response getResponse(int method) {
        switch (method) {
            case ApiResponse.GET -> {return getGetResponse();}
            case ApiResponse.POST -> {return getPostResponse();}
            case ApiResponse.PUT -> {return getPutResponse();}
            case ApiResponse.DELETE -> {return getDeleteResponse();}
        }
        throw new RuntimeException("Invalid method");
    }

    public Response getGetResponse() {
        return responseHandler.getNextResponse();
    }

    public Response getPostResponse() {
        return responseHandler.getNextResponse();
    }

    public Response getPutResponse() {
        return responseHandler.getNextResponse();
    }

    public Response getDeleteResponse() {
        return responseHandler.getNextResponse();
    }
}
