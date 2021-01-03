package edu.illinois.web.distgrep.server;

import java.net.Socket;

/**
 * @author Yichen
 */
public class ServerThread extends Thread{
    private Socket sock;

    public ServerThread(Socket sock) {
        this.sock = sock;
    }
}
