package edu.illinois.web.distgrep.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Yichen
 */
public class Server {

    private int port;

    public Server(String port) {
        this.port = Integer.parseInt(port);
    }

    public void start() {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server socket created at - " + ss.getInetAddress() + ":" + ss.getLocalPort());

            while (true) {
                ServerThread thread = null;

                try {
                    thread = new ServerThread(ss.accept());
                } catch (IOException e) {
                    System.err.println("[ERROR] Server thread creation failure!");
                    e.printStackTrace();
                    return;
                }

                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("[ERROR] Server thread execution failure!");
                    e.printStackTrace();
                    return;
                }
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Serversocket failure!");
            e.printStackTrace();
            return;
        }
    }
}
