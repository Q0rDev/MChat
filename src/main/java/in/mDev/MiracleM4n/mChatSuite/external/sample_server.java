package in.mDev.MiracleM4n.mChatSuite.external;

import java.io.*;
import java.net.*;

public class sample_server {

    // Listen for incoming connections and handle them
    public static void main(String[] args) {
        int i=0;

        try{
            int port = 4444;
            ServerSocket listener = new ServerSocket(port);
            Socket server;

            int maxConnections = 0;
            while((i++ < maxConnections) || (maxConnections == 0)){
                doComms connection;

                server = listener.accept();
                doComms conn_c= new doComms(server);
                Thread t = new Thread(conn_c);
                t.start();
            }
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }

}

class doComms implements Runnable {
    private Socket server;
    private String line, input;

    doComms(Socket server) {
        this.server=server;
    }

    public void run () {

        input = "";

        try {
            // Get input from the client
            DataInputStream in = new DataInputStream (server.getInputStream());
            PrintStream out = new PrintStream(server.getOutputStream());

            while((line = in.readLine()) != null && !line.equals(".")) {
                input=input + line;
                out.println("I got:" + line);
            }

            // Now write to the client

            System.out.println("Overall message is:" + input);
            out.println("Overall message is:" + input);

            server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}
