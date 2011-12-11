package in.mDev.MiracleM4n.mChatSuite;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.IOException;

public class MLanguageListener {
    mChatSuite plugin;

    public MLanguageListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    Boolean hasChanged = false;

    String pDied = "has died!";
    String pDamaged = "has lost health!";
    String yDied = "You have died!";
    String yDamaged = "You have lost health!";
    String yHas = "You have";
    String pHLeft = "health left.";
      String typingMessage = "*Typing*";
    String spoutChatColour = "dark_red";
    String configUpdated = "has been updated.";
    String sAFK = "You are still AFK.";
    String noPerm = "You are not allowed to use";
    String cReload = "Config Reloaded.";
    String nAFK = "is no longer AFK.";
    String iAFK = "is now AFK.";
    String AFK = "mAFK";
    String pOffline = "Players Online";
    String player = "Player";
    String nFound = "not found.";

    void load() {
        plugin.mELocale = YamlConfiguration.loadConfiguration(plugin.mELocaleF);
    }

    void loadLocale() {
        YamlConfiguration mELocale = plugin.mELocale;

        spoutChatColour = mELocale.getString("mchat-spout-colouring", spoutChatColour);
        typingMessage = mELocale.getString("mchat-typing-message", typingMessage);
        pDied = mELocale.getString("mchat-pDied", pDied);
        pDamaged = mELocale.getString("mchat-pDamaged", pDamaged);
        pHLeft = mELocale.getString("mchat-pHLeft", pHLeft);
        yDied = mELocale.getString("mchat-yDied", yDied);
        yDamaged = mELocale.getString("mchat-yDamaged", yDamaged);
        yHas = mELocale.getString("mchat-yHas", yHas);
        configUpdated = mELocale.getString("mchat-config-updated.", configUpdated);
        sAFK = mELocale.getString("mchat-sAFK.", sAFK);
        noPerm = mELocale.getString("mchat-nPerm", noPerm);
        cReload = mELocale.getString("mchat-cReloaded", cReload);
        nAFK = mELocale.getString("mchat-nLAFK", nAFK);
        iAFK = mELocale.getString("mchat-nAFK", iAFK);
        AFK = mELocale.getString("mchat-mAFK", AFK);
        pOffline = mELocale.getString("mchat-pOnline", pOffline);
        player = mELocale.getString("mchat-player", player);
        nFound = mELocale.getString("mchat-nFound", nFound);
    }

    void defaultLocale() {
        YamlConfiguration mELocale = plugin.mELocale;
        YamlConfigurationOptions mELocaleO = mELocale.options();

        mELocaleO.header(
            " mChatEssentials Locale file."
        );

        mELocale.set("mchat-spout-colouring", spoutChatColour);
        mELocale.set("mchat-typing-message", typingMessage);
        mELocale.set("mchat-pDied", pDied);
        mELocale.set("mchat-pDamaged", pDamaged);
        mELocale.set("mchat-pHLeft", pHLeft);
        mELocale.set("mchat-yDied", yDied);
        mELocale.set("mchat-yDamaged", yDamaged);
        mELocale.set("mchat-yHas", yHas);
        mELocale.set("mchat-config-updated.", configUpdated);
        mELocale.set("mchat-sAFK.", sAFK);
        mELocale.set("mchat-nPerm", noPerm);
        mELocale.set("mchat-cReloaded", cReload);
        mELocale.set("mchat-nLAFK", nAFK);
        mELocale.set("mchat-nAFK", iAFK);
        mELocale.set("mchat-mAFK", AFK);
        mELocale.set("mchat-pOnline", pOffline);
        mELocale.set("mchat-player", player);
        mELocale.set("mchat-nFound", nFound);

        try {
            mELocale.save(plugin.mELocaleF);
        } catch (IOException ignored) {}
    }

    void checkLocale() {
        if (!(plugin.mELocaleF).exists())
            defaultLocale();

        YamlConfiguration mELocale = plugin.mELocale;
        YamlConfigurationOptions mELocaleO = mELocale.options();

        checkOption(mELocale, "mchat-spout-colouring", spoutChatColour);
        checkOption(mELocale, "mchat-typing-message", typingMessage);
        checkOption(mELocale, "mchat-pDied", pDied);
        checkOption(mELocale, "mchat-pDamaged", pDamaged);
        checkOption(mELocale, "mchat-pHLeft", pHLeft);
        checkOption(mELocale, "mchat-yDied", yDied);
        checkOption(mELocale, "mchat-yDamaged", yDamaged);
        checkOption(mELocale, "mchat-yHas", yHas);
        checkOption(mELocale, "mchat-config-updated.", configUpdated);
        checkOption(mELocale, "mchat-sAFK.", sAFK);
        checkOption(mELocale, "mchat-nPerm", noPerm);
        checkOption(mELocale, "mchat-cReloaded", cReload);
        checkOption(mELocale, "mchat-nLAFK", nAFK);
        checkOption(mELocale, "mchat-nAFK", iAFK);
        checkOption(mELocale, "mchat-mAFK", AFK);
        checkOption(mELocale, "mchat-pOnline", pOffline);
        checkOption(mELocale, "mchat-player", player);
        checkOption(mELocale, "mchat-nFound", nFound);

        if (hasChanged) {
            mELocaleO.header(
                " mChatEssentials Locale file."
            );

            try {
                mELocale.save(plugin.mELocaleF);
            } catch (IOException ignored) {}

            System.out.println("[" + plugin.pdfFile.getName() + "] mELocale.yml " + configUpdated);
        }
    }

    void checkOption(YamlConfiguration config, String option, Object dOption) {
        if (config.get(option) == null) {
            config.set(option, dOption);
            hasChanged = true;
        }
    }
}
