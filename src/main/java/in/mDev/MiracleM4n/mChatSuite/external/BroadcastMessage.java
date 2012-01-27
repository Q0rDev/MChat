package in.mDev.MiracleM4n.mChatSuite.external;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import java.io.*;
import java.net.*;

public class BroadcastMessage {
    mChatSuite plugin;
    ServerSocket listener;
    InetSocketAddress address;
    String host;
    Integer port;

    public BroadcastMessage(mChatSuite plugin) {
        this.plugin = plugin;
        this.host = plugin.eBroadcastIP;
        this.port = plugin.eBroadcastPort;
    }
    
    public Boolean isConnected = false;

    public Boolean connect() {
        try {
            if (host.equalsIgnoreCase("ANY") || host.equalsIgnoreCase("0.0.0.0"))
                address = new InetSocketAddress(port);
            else
                address = new InetSocketAddress(host, port);

            listener = new ServerSocket();

            listener.setSoTimeout(1);
            listener.setReuseAddress(true);

            listener.bind(address);

            isConnected = true;
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void disconnect() {
        if (isConnected)
            try{
                if (!listener.isClosed())
                    listener.close();

                isConnected = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void startListeners() {
        plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                try {
                    Socket server = null;
                    Boolean isAccepted = false;

                    try {
                        server = listener.accept();
                        isAccepted = true;
                    } catch (Exception ignored) {}

                    if (isAccepted) {
                        server.setKeepAlive(true);
                        server.setReuseAddress(true);

                        plugin.queryList.add(server);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5L, 5L);
    }

    public void checkState() {
        for (Socket server : plugin.queryList) {
            if (!server.isConnected() || server.isClosed()) {
                try {
                    server.close();
                    plugin.queryList.remove(server);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String message) {
        for (Socket server : plugin.queryList) {
            try {
                PrintStream out = new PrintStream(server.getOutputStream());

                out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
