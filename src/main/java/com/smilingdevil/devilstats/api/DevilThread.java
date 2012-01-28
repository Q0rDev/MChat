package com.smilingdevil.devilstats.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author SmilingDevil
 */
public class DevilThread implements Runnable {

    String _url = "";
    DevilStats hook;

    public DevilThread(String url, DevilStats hook) {
        this._url = url;
        this.hook = hook;

    }

    @Override
    public void run() {
        try {
            URL url = new URL(_url);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;

            if ((inputLine = in.readLine()) != null)
                parseResults(inputLine);
            in.close();
        } catch (Exception ignored) {}
    }

    private void parseResults(String result) {
        if (result.equals("SUCCESS")) {
            hook.log("Successfully logged startup for " + hook.getPlugin()
                    + " version " + hook.getVersion());
        }
    }
}
