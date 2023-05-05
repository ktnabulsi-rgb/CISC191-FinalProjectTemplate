package edu.sdccd.cisc191.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//TODO move to Server
public class Network {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;

    public Network(String ip, int port) {
        this.ip = "127.0.0.1";
        this.port = 4444;
    }
    public void startConnection(String ip, int port) throws IOException {
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

    public void sendLog() {
        //TODO boolean return type to determine if log was sent
        try {
            startConnection(ip, port);
            //TODO Parse and return response boolean
            System.out.println(sendRequest().toString());
            stopConnection();
        } catch(Exception e) {
            e.printStackTrace();
            //TODO return false if there is an error (connection would be unsuccsessful)
        }
    }
}
