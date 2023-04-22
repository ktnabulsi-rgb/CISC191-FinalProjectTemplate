package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * This program is a server that takes connection requests on
 * the port specified by the constant LISTENING_PORT.  When a
 * connection is opened, the program sends the current time to
 * the connected socket.  The program will continue to receive
 * and process connections until it is killed (by a CONTROL-C,
 * for example).  Note that this server processes each connection
 * as it is received, rather than creating a separate thread
 * to process the connection.
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;



    /*public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            CustomerRequest request = CustomerRequest.fromJSON(inputLine);
            CustomerResponse response = new CustomerResponse(request.getId(), "Jane", "Doe");
          out.println(CustomerResponse.toJSON(response));
      }
   }*/

    //public void stop() throws IOException {
       // in.close();
        //out.close();
        //clientSocket.close();
        //serverSocket.close();
   // }

    public static void main(String[] args) throws IOException{
        /*Server server = new Server();
        try {
            server.start(4444);
            server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }*/
        //start here

        LinkedList<String> calcHistory = new LinkedList<>();

        try {
            calcHistory = IOHelper.readItems("output.txt");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ServerSocket ss = new ServerSocket(4999);

        Socket s = ss.accept();
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("client connected");


        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String inputLine;

        while ((inputLine = bf.readLine()) != null) {

            if (inputLine.equals(HistoryRequest.HR)) {
                System.out.println("client : " + inputLine);
                for(int i = 0; i < calcHistory.size(); i++) {
                    //use println instead of write so client can read off of start
                    out.println(calcHistory.get(i));
                }
            }
//            else if() {
//                //TODO - create and handle history saved request
//            }

        }









        //InetAddress inetAddress = ss.getInetAddress();
        //System.out.println(inetAddress);

    }


} //end class Server
