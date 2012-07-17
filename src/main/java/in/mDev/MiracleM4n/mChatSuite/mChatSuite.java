package in.mDev.MiracleM4n.mChatSuite;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.metrics.Metrics;
import in.mDev.MiracleM4n.mChatSuite.api.API;
import in.mDev.MiracleM4n.mChatSuite.api.Parser;
import in.mDev.MiracleM4n.mChatSuite.api.Reader;
import in.mDev.MiracleM4n.mChatSuite.api.Writer;

import java.io.IOException;

public class mChatSuite extends MChat {
    // External Class Relays
    API api;
    Parser parser;
    Writer writer;
    Reader reader;

    public void onEnable() {
        try {
            super.metrics = new Metrics(this);
            super.metrics.start();
        } catch (IOException ignored) { }

        initializeClasses();

        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    void initializeClasses() {
        api = new API();
        parser = new Parser();
        reader = new Reader();
        writer = new Writer();
    }

    // API
    @Deprecated
    public API getAPI() {
        return api;
    }

    // Parser
    @Deprecated
    public Parser getParser() {
        return parser;
    }

    // Reader
    @Deprecated
    public Reader getReader() {
        return reader;
    }

    // Writer
    @Deprecated
    public Writer getWriter() {
        return writer;
    }
}
