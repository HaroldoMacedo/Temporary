package haroldo.poc;

import com.sun.net.httpserver.HttpHandler;
import haroldo.poc.api.Api;
import haroldo.poc.api.ApiImpl;
import haroldo.poc.api.ApiResponse;
import haroldo.poc.api.DefaultApiResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NodeTest {
    @Test
    public void startListenerTest() throws IOException {
        Node.createListener(8081);
        Node.startListener(8081);
        sleepSeconds(20);
        System.out.println("End of starting listener test");
    }

    @Test
    public void stopListenerTest() throws IOException {
        Node.startListener(8081);
        sleepSeconds(10);
        Node.stopListener(8081);
        sleepSeconds(20);
        System.out.println("End of stopping listener test");
    }

    @Test
    void doubleListenerCreationTest() {
        Node.createListener(8081);
        sleepSeconds(1);

        Node.createListener(8081);
        sleepSeconds(20);
        System.out.println("End of double listener test");
    }

    @Test
    void deployApplicationTest() {
        Node.createListener(8081);

        Api api = new ApiImpl("/hello", 10);
        ApiResponse apiResponse = new DefaultApiResponse("Hello World!", api.getAvgResponseTimeMs());
        HttpHandler httpHandler = new DefaultHttpHandler(10, apiResponse);
        DeployedApplication deployedApplication = new DeployedApplication("Hello", api, httpHandler);
        Node.deployApplication(8081, deployedApplication);

        sleepSeconds(20);
        System.out.println("End of deploy application test");
    }

    @Test
    void undeployApplicationTest() {
        Node.createListener(8081);

        Api api = new ApiImpl("/hello", 10);
        ApiResponse apiResponse = new DefaultApiResponse("Hello World!", api.getAvgResponseTimeMs());
        HttpHandler httpHandler = new DefaultHttpHandler(10, apiResponse);
        DeployedApplication deployedApplication = new DeployedApplication("Hello", api, httpHandler);
        Node.deployApplication(8081, deployedApplication);

        Node.undeployApplication(8081, deployedApplication.getName());
        Node.stopApplication(8081, deployedApplication.getName());
        sleepSeconds(20);

        System.out.println("End of undeploy application test");
    }

    @Test
    void startApplicationTest() throws IOException {
        createAndStartApplication(8081, "Hello!", "/hello");

        sleepSeconds(20);
        System.out.println("End of start application test");
    }

    @Test
    void startApplicationTwiceTest() throws IOException {
        createAndStartApplication(8081, "Hello!", "/hello");
        try {
            Node.startApplication(8081, "Hello!");
            assert (false);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        System.out.println("End of start application twice test");
    }

    @Test
    void stopApplicationTest() throws IOException {
        createAndStartApplication(8081, "Hello!", "/hello");
        sleepSeconds(10);

        Node.stopApplication(8081, "Hello!");
        sleepSeconds(20);

        System.out.println("End of stop application test");
    }

    private void createAndStartApplication(int port, String name, String uri) throws IOException {
        Node.startListener(port);

        Api api = new ApiImpl(uri, 10);
        ApiResponse apiResponse = new DefaultApiResponse("Hello World!", api.getAvgResponseTimeMs());
        HttpHandler httpHandler = new DefaultHttpHandler(10, apiResponse);
        DeployedApplication deployedApplication = new DeployedApplication(name, api, httpHandler);
        Node.deployApplication(port, deployedApplication);
        Node.startApplication(port, name);
    }

    private void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {}
    }
}
