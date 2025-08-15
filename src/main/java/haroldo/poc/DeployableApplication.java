package haroldo.poc;

import com.sun.net.httpserver.HttpHandler;
import haroldo.poc.api.Api;

public class DeployableApplication {
    private final String name;
    private final Api api;
    private final HttpHandler httpHandler;

    public DeployableApplication(String name, Api api, int maxThroughPutPerSecond) {
        this.name = name;
        this.api = api;
        this.httpHandler = new DefaultHttpHandler(maxThroughPutPerSecond, api.getApiResponse());
    }

    public String getName() {
        return name;
    }

    public Api getApi() {
        return api;
    }

    public HttpHandler getApplicationHandle() {
        return httpHandler;
    }
}
