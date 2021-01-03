package edu.illinois.web.distgrep.server;

public class ServerUserInterface {
    public static void main(String[] args) {
        String port = args[0];
        Server s = new Server(port);
        s.start();
    }
}
