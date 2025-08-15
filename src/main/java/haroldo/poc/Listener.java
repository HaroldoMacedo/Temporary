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
    List<DeployableApplication> deployableApplicationList = new ArrayList<>();

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

    public void deployApplication(DeployableApplication deployableApplication) {
        deployableApplicationList.add(deployableApplication);
    }

    public void undeployApplication(String applicationName) {
        deployableApplicationList.removeIf(deployableApplication -> deployableApplication.getName().equals(applicationName));
    }

    public void startApplication(String applicationName) {
        DeployableApplication deployableApplication = getApplicationByName(applicationName);
        if (deployableApplication == null)
            return;

        server.createContext(deployableApplication.getApi().getUri(), deployableApplication.getApplicationHandle());
    }

    public void stopApplication(String applicationName) {
        DeployableApplication deployableApplication = getApplicationByName(applicationName);
        if (deployableApplication == null)
            return;

        server.removeContext(deployableApplication.getApi().getUri());
    }

    private DeployableApplication getApplicationByName(String applicationName) {
        return deployableApplicationList.stream()
                .filter(deployableApplication -> deployableApplication.getName().equals(applicationName))
                .findFirst()
                .orElse(null);
    }
}


