package ca.q0r.mchat.yml.locale;

import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;

/**
 * Enum for Different Locale Values.
 */
public enum LocaleType {
    /** Forward Format */ FORMAT_FORWARD("format.forward"),
    /** Local Format */ FORMAT_LOCAL("format.local"),
    /** Spy Format */ FORMAT_SPY("format.spy"),
    /** Chat Format */ FORMAT_CHAT("format.chat"),
    /** Date Format */ FORMAT_DATE("format.date"),
    /** Name Format */ FORMAT_NAME("format.name"),
    /** Tabbed List Format */ FORMAT_TABBED_LIST("format.tabbedList"),
    /** List Command Format */ FORMAT_LIST_CMD("format.listCmd"),
    /** Me Format */ FORMAT_ME("format.me"),

    /** Info Alteration Message */ MESSAGE_INFO_ALTERATION("message.info.alteration"),
    /** No Permissions Message */ MESSAGE_NO_PERMS("message.general.noPerms"),
    /** Join Event Message */ MESSAGE_EVENT_JOIN("message.event.join"),
    /** Leave Event Message */ MESSAGE_EVENT_LEAVE("message.event.leave"),
    /** Kick Event Message */ MESSAGE_EVENT_KICK("message.event.kick"),
    /** Heroes Mastered Message */ MESSAGE_HEROES_TRUE("message.heroes.isMaster"),
    /** Heroes Not Mastered Message */ MESSAGE_HEROES_FALSE("message.heroes.notMaster");

    private final String option;

    private LocaleType(String option) {
        this.option = option;
    }

    /**
     * Value Retriever.
     *
     * @return Retrieves Value and and Colours it.
     */
    public String getVal() {
        return MessageUtil.addColour(getRaw());
    }

    /**
     * Value Retriever.
     *
     * @return Retrieves Raw Value.
     */
    public String getRaw() {
        if (YmlManager.getYml(YmlType.LOCALE_YML).getConfig().isSet(option)) {
            return YmlManager.getYml(YmlType.LOCALE_YML).getConfig().getString(option);
        }

        return "Locale Option '" + option + "' not found!";
    }
}