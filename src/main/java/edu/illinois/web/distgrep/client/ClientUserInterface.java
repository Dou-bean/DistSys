package edu.illinois.web.distgrep.client;

import edu.illinois.web.distgrep.ConfigurationBean;
import edu.illinois.web.distgrep.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Yichen
 */
public class ClientUserInterface {

    private static final String LOG_PATH = "/Users/Yichen 1/gitlab/cs425fa20/MP0/Java/outputs/";

    public static void main(String[] args){
        System.out.println("Application started.");

        // create servers from json configuration file
        List<ConfigurationBean> servers = Utility.getServers();

        String logPath;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your grep query:");
        logPath = in.nextLine();
        if (logPath == null || "".equals(logPath)) {
            logPath = LOG_PATH;
        }

        String query;
        System.out.println("Enter your grep query:");
        query = in.nextLine();
        Client client = new Client(servers, logPath, query);

        while (!query.equals("exit")) {
            client.setQuery(query);
            System.out.println("Executing " + query + "...");
            client.execute();
            System.out.println("Query completed.");

            // enter the next query command
            System.out.println("Enter your grep query:");
            query = in.nextLine();
        }

        return;
    }
}
