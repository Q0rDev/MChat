package com.miraclem4n.mchat.modules;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

public abstract class MChatModule {
    mChatSuite mchatz;

    public MChatModule(mChatSuite mchat) {
        mchatz = mchat;
    }

    public void registerCommands() {}
    public void registerEvents() {}
    public void registerTasks() {}

    public void onHook(mChatSuite mchat) {}
    public void onUnHook(mChatSuite mchat) {}
}
