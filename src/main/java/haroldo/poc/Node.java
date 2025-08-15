package haroldo.poc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that mimics or runs in a node on a JVM server.
 */
public class Node {
    private final static Map<Integer, Listener> listenerList = new HashMap<>();

    private Node() {
    }

    public static Listener createListener(int port) {
        Listener listener = listenerList.get(port);
        if (listener != null)
            return listener;
        System.out.println("Creating listener on port " + port);
        listener = new Listener(port);
        listenerList.put(port, listener);
        System.out.println("Listener created on port " + port);

        return listener;
    }

    public static void startListener(int port) throws IOException {
        System.out.println("Starting listener on port " + port);
        Listener listener = createListener(port);
        listener.start();
        System.out.println("Listener started on port " + port);
    }

    public static void stopListener(int port) {
        System.out.println("Stopping listener on port " + port);
        Listener listener = listenerList.get(port);
        if (listener == null)
            return;
        listener.stop();
        System.out.println("Listener stopped on port " + port);
    }

    public static void deployApplication(int port, DeployedApplication deployedApplication) {
        System.out.println("Deploying application " + deployedApplication.getName() + " on port " + port);
        Listener listener = listenerList.get(port);
        if (listener == null)
            return;
        listener.deployApplication(deployedApplication);
        System.out.println("Application " + deployedApplication.getName() + " deployed on port " + port);
    }

    public static void undeployApplication(int port, String name) {
        System.out.println("Undeploying application " + name + " on port " + port);
        Listener listener = listenerList.get(port);
        if (listener == null)
            return;
        listener.undeployApplication(name);
        System.out.println("Application " + name + " undeployed on port " + port);
    }

    public static void startApplication(int port, String applicationName) {
        System.out.println("Starting application " + applicationName + " on port " + port);
        Listener listener = listenerList.get(port);
        if (listener == null)
            return;
        listener.startApplication(applicationName);
        System.out.println("Application " + applicationName + " started on port " + port);
    }

    public static void stopApplication(int port, String applicationName) {
        System.out.println("Stopping application " + applicationName + " on port " + port);
        Listener listener = listenerList.get(port);
        if (listener == null)
            return;
        listener.stopApplication(applicationName);
        System.out.println("Application " + applicationName + " stopped on port " + port);
    }
}
