package in.mDev.MiracleM4n.mChatSuite.external;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

@SuppressWarnings("unused")
public final class Query {
    /**
     * The javaplugin
     */
    final mChatSuite plugin;
    /**
     * The host that the server will listen on.
     */
    private final String host;

    /**
     * The Query port.
     */
    private final int port;

    /**
     * The connection listener.
     */
    private Socket listener;

    /**
     * Creates a new <code>QueryServer</code> object.
     *
     * @param plugin
     * 			  The plugin that is initializing this.
     * @param host
     * 			  The host that this server will bind to.
     * @param port
     *            The port that this server will bind on.
     */
    public Query(mChatSuite plugin, String host, int port) {
        this.host = host;
        this.port = port;
        this.plugin = plugin;
    }

    public boolean close() {
        try {
            listener.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Starts the ServerSocket listener.
     */
    public void startListener() {
        try {
            // Initialize the listener.
            InetSocketAddress address;
            if (host.equalsIgnoreCase("ANY") || host.equalsIgnoreCase("0.0.0.0"))
                address = new InetSocketAddress(port);

            else
                address = new InetSocketAddress(host, port);

            listener = new Socket();
            listener.bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the Data.
     *
     * @param message The message to be sent.
     */
    public void sendData(String message) {
        if (listener.isConnected()) {
            try {
                PrintWriter out = new PrintWriter(listener.getOutputStream());

                listener.setTcpNoDelay(true);

                System.out.println(message);

                out.print(message);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the <code>QueryServer</code> host.
     *
     * @return The host, default ANY
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the <code>QueryServer</code> port.
     *
     * @return The port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the listening <code>ServerSocket</code>.
     *
     * @return The server socket
     */
    public Socket getListener() {
        return listener;
    }

}
