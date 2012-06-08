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
    static URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    public static Boolean include(String directory, String filename, String url) {
        if (!download(directory, filename, url))
            return false;

        File file = getFile(directory, filename);

        return load(file) != null;
    }

    public static Boolean download(String directory, String filename, String url) {
        File file = getFile(directory, filename);

        if (!file.exists()) {
            MessageUtil.log("Downloading library " + filename);

            if (!downloadUrl(url, file)) {
                MessageUtil.log("Failed to download " + filename);
                return false;
            }
        }

        return true;
    }

    static File getFile(String directory, String filename) {
        if (directory == null || directory.isEmpty())
            return  new File("./lib/" + filename);

        return new File(directory, filename);
    }

    static File getFile(String filename) {
        return  new File("./lib/" + filename);
    }

    static Boolean downloadUrl(String url, File file) {
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

    public static Object load(File file) {
        try {
            return load(file.toURI().toURL());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static Object load(URL url) {
        for (URL otherUrl : loader.getURLs())
            if (otherUrl.sameFile(url))
                return null;

        try {
            Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
            return addURLMethod.invoke(loader, url);
        } catch (Exception e) {
            return null;
        }
    }
}
