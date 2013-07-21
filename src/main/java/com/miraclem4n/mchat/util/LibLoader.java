package com.miraclem4n.mchat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LibLoader {
    private static URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    public static Boolean include(String filename, String url) {
        if (!download(filename, url)) {
            return false;
        }

        File file = getFile(filename);

        return load(file);
    }

    private static Boolean download(String filename, String url) {
        File file = getFile(filename);

        if (!file.exists()) {
            MessageUtil.log("Downloading library " + filename);

            if (!downloadUrl(url, file)) {
                MessageUtil.log("Failed to download " + filename);
                return false;
            }
        }

        return true;
    }

    private static File getFile(String filename) {
        Boolean dirCreated = false;

        if (!new File("./lib").exists()) {
            dirCreated = new File("./lib").mkdirs();
        }

        if (dirCreated) {
            MessageUtil.log("Lib Directory Created.");
        }

        return  new File("./lib/" + filename);
    }

    private static Boolean downloadUrl(String url, File file) {
        try {
            URL urlz = new URL(url);
            ReadableByteChannel ch = Channels.newChannel(urlz.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(ch, 0, 1 << 24);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    private static Boolean load(File file) {
        try {
            return load(file.toURI().toURL());
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private static Boolean load(URL url) {
        for (URL otherUrl : loader.getURLs()) {
            if (otherUrl.sameFile(url)) {
                return true;
            }
        }

        try {
            Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
            addURLMethod.invoke(loader, url);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
