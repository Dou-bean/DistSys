package edu.illinois.web.distgrep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Yichen
 */
public class Utility {
    private static String JsonReader(String fileName) {
        String jsonStr = "";
        try {
            File json = new File(fileName);
            FileReader fileReader = new FileReader(json);

            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject getJsonObject() throws UnsupportedEncodingException {
        String path = Utility.class.getClassLoader().getResource("configuration.json").getPath();
        path= URLDecoder.decode(path,"utf-8");
        String json = JsonReader(path);
        return JSON.parseObject(json);
    }

    public static List<ConfigurationBean> getServers() {
        List<ConfigurationBean> servers = null;
        try {
            JSONArray clusters = getJsonObject().getJSONArray("clusters");

            int n = clusters.size();
            servers = new ArrayList<>(n);

            for (int i = 0; i < n; i++) {
                JSONObject cluster = (JSONObject) clusters.get(i);
                String url = (String) cluster.get("url");
                String port = (String) cluster.get("port");
                String path = (String) cluster.get("path");
                ConfigurationBean server = new ConfigurationBean(url, port, path);
                servers.add(server);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.err.println("[Error] JSON reading failure!");
        }

        return servers;
    }

}
