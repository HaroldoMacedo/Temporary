package haroldo.poc;

import com.sun.net.httpserver.HttpHandler;
import haroldo.poc.api.Api;

public class DeployedApplication {
    private final String name;
    private final Api api;
    private final HttpHandler httpHandler;

    public DeployedApplication(String name, Api api, HttpHandler httpHandler) {
        this.name = name;
        this.api = api;
        this.httpHandler = httpHandler;
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
