package haroldo.poc.api;


public class DefaultApiResponse implements ApiResponse {

    final ResponseHandler responseHandler;

    public DefaultApiResponse(String message, long latencyMs) {
        this.responseHandler = new ResponseHandler(new Response(message, latencyMs));
    }

    @Override
    public Response getResponse(int method) {
        switch (method) {
            case ApiResponse.GET -> {return getGetResponse();}
            case ApiResponse.POST -> {return getPostResponse();}
            case ApiResponse.PUT -> {return getPutResponse();}
            case ApiResponse.DELETE -> {return getDeleteResponse();}
        }
        return responseHandler.getNextResponse();    // TODO Should I launch an exception?
    }

    @Override
    public Response getGetResponse() {
        return responseHandler.getNextResponse();
    }

    @Override
    public Response getPostResponse() {
        return responseHandler.getNextResponse();
    }

    @Override
    public Response getPutResponse() {
        return responseHandler.getNextResponse();
    }

    @Override
    public Response getDeleteResponse() {
        return responseHandler.getNextResponse();
    }
}
