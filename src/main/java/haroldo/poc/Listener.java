package haroldo.poc;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Listener {
    private final int port;
    private HttpServer server;
    List<DeployedApplication> deployedApplicationList = new ArrayList<>();

    public Listener(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
    }

    public void stop() {
        server.stop(1);
    }

    public void deployApplication(DeployedApplication deployedApplication) {
        deployedApplicationList.add(deployedApplication);
    }

    public void undeployApplication(String applicationName) {
        deployedApplicationList.removeIf(deployedApplication -> deployedApplication.getName().equals(applicationName));
    }

    public void startApplication(String applicationName) {
        DeployedApplication deployedApplication = getApplicationByName(applicationName);
        if (deployedApplication == null)
            return;

        server.createContext(deployedApplication.getApi().getUri(), deployedApplication.getApplicationHandle());
    }

    public void stopApplication(String applicationName) {
        DeployedApplication deployedApplication = getApplicationByName(applicationName);
        if (deployedApplication == null)
            return;

        server.removeContext(deployedApplication.getApi().getUri());
    }

    private DeployedApplication getApplicationByName(String applicationName) {
        return deployedApplicationList.stream()
                .filter(deployedApplication -> deployedApplication.getName().equals(applicationName))
                .findFirst()
                .orElse(null);
    }
}


