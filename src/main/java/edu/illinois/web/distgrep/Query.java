package edu.illinois.web.distgrep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Query {
    private String query;
    private String logPath;

    public Query(String query, String logPath) {
        this.query = query;
        this.logPath = logPath;
    }

    public void execute(BufferedWriter writer) throws IOException {
        Process p = Runtime.getRuntime().exec(query + " " + logPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
            writer.flush();
            System.out.println(line);
        }
    }
}
