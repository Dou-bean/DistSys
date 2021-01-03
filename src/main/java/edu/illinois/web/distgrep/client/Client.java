package edu.illinois.web.distgrep.client;

import edu.illinois.web.distgrep.ConfigurationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    private AtomicInteger totalMatch;

    private List<ConfigurationBean> servers;
    private String logPath;
    private String query;

    public Client(List<ConfigurationBean> servers, String logPath) {
        this.servers = servers;
        this.logPath = logPath;
    }

    public Client(List<ConfigurationBean> servers, String logPath, String query) {
        this(servers, logPath);
        this.query = query;
    }

    // use setter to update query to reuse an object
    public void setQuery(String query) {
        this.query = query;
    }

    public void execute() {

        totalMatch = new AtomicInteger(0);

        int n = servers.size();

        long start = System.nanoTime();

        List<edu.illinois.web.distgrep.client.ClientThread> threads = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            edu.illinois.web.distgrep.client.ClientThread thread = new edu.illinois.web.distgrep.client.ClientThread(i + 1, servers.get(i), query, logPath);
            threads.add(thread);
            thread.start();
        }

        try {
            for (edu.illinois.web.distgrep.client.ClientThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.err.println("[ERROR] Client thread fail!");
            e.printStackTrace();
            return;
        } finally {
            System.out.println("Successfully executed on " + n + " servers.");
        }

        long end = System.nanoTime();
        long elapsed = (end - start) / 1000000L;
        System.out.println("Execution time: " + Long.toString(elapsed) + "ms.");
    }

}
