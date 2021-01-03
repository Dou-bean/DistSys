package edu.illinois.web.distgrep.client;

import edu.illinois.web.distgrep.ConfigurationBean;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClientThread extends Thread{
    private int serverID;
    private ConfigurationBean server;
    private String query;
    private String logPath;

    public ClientThread(int serverID, ConfigurationBean server, String query, String logPath) {
        this.serverID = serverID;
        this.server = server;
        this.query = query;
        this.logPath = logPath;
    }

    private void sendQuery(BufferedWriter writer) throws IOException {
        writer.write(query);
        writer.newLine();
        writer.write(server.getPath());
        writer.newLine();
        writer.flush();
    }

    @Override
    public void run() {
        String serverAddr = server.getAddr();
        int serverPort = Integer.parseInt(server.getPort());
        try (Socket sock = new Socket(serverAddr, serverPort)) {
            // timeout set to 5 seconds
            sock.setSoTimeout(50000);

            // Construct BufferedReader instance from sock
            // BufferedReader provides readline while the interface reader does not
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                System.err.println("[ERROR] Failure input stream creation with server " +
                        "[" + serverAddr + ":" + serverPort + "].");
                e.printStackTrace();
                return;
            }

            // Construct BufferedWriter instance from sock
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
            } catch (IOException e) {
                System.err.println("[ERROR] Failure output stream creation with server " +
                        "[" + serverAddr + ":" + serverPort + "].");
                e.printStackTrace();
                return;
            }

            // Send query to the socket output
            try {
                sendQuery(writer);
            } catch (IOException e) {
                System.err.println("[ERROR] Failure query sending to server " +
                        "[" + serverAddr + ":" + serverPort + "].");
                e.printStackTrace();
                return;
            }

            // get output from server and log into file
            int count = 0;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            File f = new File(server.getPath() + serverID + sdf.format(date) + ".txt");
            try (FileWriter fw = new FileWriter(f)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fw.write(line);
                    fw.write("\n");
                    fw.flush();
                    count++;
                }
                System.out.println("Server [" + serverAddr + ":" + serverPort + "] has " + count + " matched lines.");
            } catch (IOException e) {
                System.err.println("[ERROR] Failure file Writing.");
                e.printStackTrace();
                return;
            }


        } catch (UnknownHostException e) {
            System.err.println("[ERROR] Server [" + serverAddr + ":" + serverPort + "] unknown.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.err.println("[ERROR] Server [" + serverAddr + ":" + serverPort + "] failure.");
            e.printStackTrace();
            return;
        } finally {
            System.out.println("Server [" + serverAddr + ":" + serverPort + "] socket connection successful.");
        }

    }
}
