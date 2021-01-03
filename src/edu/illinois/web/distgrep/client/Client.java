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

    public void setQuery(String query) {
        this.query = query;
    }

    public void execute() {

        totalMatch = new AtomicInteger(0);

        int n = servers.size();

        long start = System.nanoTime();

        List<ClientThread> threads = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ClientThread thread = new ClientThread(i + 1, servers.get(i), query, logPath);
            threads.add(thread);
            thread.start();
        }

        try {
            for (ClientThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.err.println("[ERROR] Client thread fail!");
            e.printStackTrace();
            return;
        } finally {
            System.out.println("Successfully executed on " + n + "servers.");
        }

        long end = System.nanoTime();
        long elapsed = (end - start) / 1000000L;
        System.out.println("Execution time: " + Long.toString(elapsed) + "ms.");
    }

    public static void main(String[] args){
        System.out.println("Client application started.");

        ConfigurationBean config = new ConfigurationBean(
                "fa20-cs427-230.cs.illinois.edu",
                "3001",
                "/home/yy20/distributed-log-querier/logs/test1.log"
                );

        List<ConfigurationBean> servers = new ArrayList<>();
        servers.add(config);

        String query;
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Enter the grep command that you want to query (e.g grep -E ^[0-9]*[a-z]{5}):");
        query = in.nextLine();
        Client client = new Client(servers, "/Users/Yichen 1/gitlab/cs425fa20/MP0/Java/outputs/", query);

        while (!query.equals("exit")) {
            client.setQuery(query);
            System.out.println("Executing " + query + "...");
            client.execute();
            System.out.println("Query completed. See the result in the folder \"outputs/\"");

            // enter the next query command
            System.out.println("Enter the grep command that you want to query (e.g grep -E ^[0-9]*[a-z]{5}):");
            query = in.nextLine();
        }

        return;
    }

}
