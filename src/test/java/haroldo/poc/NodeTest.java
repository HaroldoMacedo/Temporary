package haroldo.poc;

import haroldo.poc.api.Api;
import haroldo.poc.api.DefaultApi;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NodeTest {
    private int port = 8081;
    private String uri = "/hello";
    private String appName = "Hello!";


    @Test
    public void startListenerTest() throws IOException {
        System.out.println("Start of starting listener test");
        Node.createListener(port);
        Node.startListener(port);
        assert (TestUtils.isPortOpen(port));

        Node.stopListener(port);
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of starting listener test");
    }

    @Test
    public void stopListenerTest() throws IOException {
        System.out.println("Start of stopping listener test");
        Node.startListener(port);
        assert (TestUtils.isPortOpen(port));
        Node.stopListener(port);
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of stopping listener test");
    }

    @Test
    void doubleListenerCreationTest() {
        System.out.println("Start of double listener test");
        Node.createListener(port);
        Node.createListener(port);

        assert (!TestUtils.isPortOpen(port));

        Node.stopListener(port);
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of double listener test");
    }

    @Test
    void deployApplicationTest() {
        System.out.println("Start of deploying application test");

        Api api = new DefaultApi(uri, "Hello World!", 100);
        DeployableApplication deployableApplication = new DeployableApplication("Hello", api, 10);

        Node.createListener(port);
        Node.deployApplication(port, deployableApplication);

        System.out.println("End of deploy application test");

    }

    @Test
    void undeployApplicationTest() {
        System.out.println("Start of undeploying application test");

        Api api = new DefaultApi(uri, "Hello World!", 100);
        DeployableApplication deployableApplication = new DeployableApplication("Hello", api, 10);

        Node.createListener(port);
        Node.deployApplication(port, deployableApplication);

        Node.undeployApplication(port, deployableApplication.getName());
        Node.stopApplication(port, deployableApplication.getName());

        System.out.println("End of undeploy application test");
    }

    @Test
    void startApplicationTest() throws IOException {
        System.out.println("Start of starting application test");
        createAndStartApplication(port, appName, uri);

        Node.stopListener(port);
        assert (!TestUtils.isEndpointResponding(port, uri));
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of start application test");
    }

    @Test
    void startApplicationTwiceTest() throws IOException {
        System.out.println("Start of starting application test");
        createAndStartApplication(port, appName, uri);
        try {
            Node.startApplication(port, appName);
            assert (false);
        } catch (IllegalArgumentException e) {
            assert (true);
        }

        Node.stopListener(port);
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of start application twice test");
    }

    @Test
    void stopApplicationTest() throws IOException {
        System.out.println("Start of stopping application test");
        createAndStartApplication(port, appName, uri);

        Node.stopApplication(port, appName);
        TestUtils.sleepSeconds(1);
        assert (!TestUtils.isEndpointResponding(port, uri));

        Node.stopListener(port);
        assert (!TestUtils.isPortOpen(port));
        System.out.println("End of stop application test");
    }

    private void createAndStartApplication(int port, String name, String uri) throws IOException {
        assert (!TestUtils.isPortOpen(port));
        assert (!TestUtils.isEndpointResponding(port, uri));

        Api api = new DefaultApi(uri);
        DeployableApplication deployableApplication = new DeployableApplication(name, api, 10);

        Node.startListener(port);
        assert (TestUtils.isPortOpen(port));

        Node.deployApplication(port, deployableApplication);
        Node.startApplication(port, name);
        assert (TestUtils.isPortOpen(port));
        assert (TestUtils.isEndpointResponding(port, uri));
    }

}
