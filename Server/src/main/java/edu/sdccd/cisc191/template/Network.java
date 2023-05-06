package edu.sdccd.cisc191.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Network {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;
    private boolean logSent;

    public Network(String ip, int port) {
        this.ip = "127.0.0.1";
        this.port = 4444;
        logSent = false;
    }
    public void startConnection(String ip, int port) throws IOException {
        System.out.println("Client started");
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public LogResponse sendRequest() throws Exception {
        out.println(LogRequest.toJSON(new LogRequest(1)));
        return LogResponse.fromJSON(in.readLine());
    }

    public boolean sendLog() {
        try {
            startConnection(ip, port);
            LogResponse response = sendRequest();
            System.out.println(sendRequest().toString());
            logSent = true;
            stopConnection();
        } catch(Exception e) {
            System.out.println("Connection was unsuccessful.");
            logSent = false;
            e.printStackTrace();
        }
        return logSent;
    }
}
