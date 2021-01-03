package edu.illinois.web.distgrep.server;

import edu.illinois.web.distgrep.Query;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Yichen
 */
public class ServerThread extends Thread{
    private Socket sock;

    private int localPort;
    private String localAddr;

    public ServerThread(Socket sock) throws UnknownHostException {
        this.sock = sock;
        this.localPort = sock.getLocalPort();
        this.localAddr = InetAddress.getLocalHost().getHostAddress();
    }

    private void close() {
        try {
            sock.close();
        } catch (IOException e) {
            System.err.println("[ERROR] Server socket close failure!");
            e.printStackTrace();
            return;
        }
    }

    private void handler(BufferedReader reader, BufferedWriter writer) throws IOException {
        String query = reader.readLine();
        String logPath = reader.readLine();

        Query q = new Query(query, logPath);
        q.execute(writer);
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            System.err.println("[ERROR] Failure input stream creation on server.");
            e.printStackTrace();
            return;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
        } catch (IOException e) {
            System.err.println("[ERROR] Failure output stream creation on server.");
            e.printStackTrace();
            return;
        }

        try {
            handler(reader, writer);
        } catch (IOException e) {
            System.err.println("[ERROR] Failure query execution at server.");
            e.printStackTrace();
            return;
        }

        close();
        System.out.println("Server side socket closed.");
    }
}
