package ca.q0r.mchat.api;

import ca.q0r.mchat.types.EventType;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.config.ConfigType;
import ca.q0r.mchat.yml.locale.LocaleType;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to parse messages / events / misc.
 */
public class Parser {
    /**
     * Core Formatting
     *
     * @param pName  Name of Player being reflected upon.
     * @param world  Player's World.
     * @param msg    Message being displayed.
     * @param format Resulting Format.
     * @return Formatted Message.
     */
    public static String parseMessage(String pName, String world, String msg, String format) {
        msg = msg != null ? msg : "";

        format = parseVars(format, pName, world);

        msg = msg.replaceAll("%", "%%");

        format = format.replaceAll("%", "%%");

        format = MessageUtil.addColour(format);

        Integer cInt = ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger();

        if (!API.checkPermissions(pName, world, "mchat.bypass.clock") && cInt > 0) {
            msg = fixCaps(msg, cInt);
        }

        if (format == null) {
            return msg;
        }

        if (API.checkPermissions(pName, world, "mchat.coloredchat")) {
            msg = MessageUtil.addColour(msg);
        }

        if (!API.checkPermissions(pName, world, "mchat.censorbypass")) {
            msg = replaceCensoredWords(msg);
        }

        format = VariableManager.replaceCustVars(pName, format);
        format = VariableManager.replaceVars(format, pName, msg, true);

        return format;
    }

    /**
     * Chat Formatting
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg   Message being displayed.
     * @return Formatted Chat Message.
     */
    public static String parseChatMessage(String pName, String world, String msg) {
        return parseMessage(pName, world, msg, LocaleType.FORMAT_CHAT.getRaw());
    }

    /**
     * Player Name Formatting
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Player Name.
     */
    public static String parsePlayerName(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_NAME.getRaw());
    }

    /**
     * Event Message Formatting
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param type  Event Type being formatted.
     * @return Formatted Event Message.
     */
    public static String parseEvent(String pName, String world, EventType type) {
        return parseMessage(pName, world, "", API.replace(Reader.getEventMessage(type), "player", parsePlayerName(pName, world), IndicatorType.LOCALE_VAR));
    }

    /**
     * TabbedList Formatting
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted TabbedList Name.
     */
    public static String parseTabbedList(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_TABBED_LIST.getRaw());
    }

    /**
     * ListCommand Formatting.
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted ListCommand Name.
     */
    public static String parseListCmd(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_LIST_CMD.getRaw());
    }

    /**
     * Me Formatting.
     *
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg   Message being displayed.
     * @return Formatted Me Message.
     */
    public static String parseMe(String pName, String world, String msg) {
        return parseMessage(pName, world, msg, LocaleType.FORMAT_ME.getRaw());
    }

    private static String fixCaps(String format, Integer range) {
        if (range < 1) {
            return format;
        }

        Pattern pattern = Pattern.compile("([A-Z]{" + range + ",300})");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group().toLowerCase()));
        }

        matcher.appendTail(sb);

        format = sb.toString();

        return format;
    }

    private static String parseVars(String format, String pName, String world) {
        String vI = "\\" + IndicatorType.MISC_VAR.getValue();
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = Reader.getRawInfo(pName, InfoType.USER, world, matcher.group(1)).toString();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String replaceCensoredWords(String msg) {
        if (ConfigType.MCHAT_IP_CENSOR.getBoolean()) {
            msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");
        }

        for (Map.Entry<String, Object> entry : YmlManager.getYml(YmlType.CENSOR_YML).getConfig().getValues(true).entrySet()) {
            if (!(entry.getValue() instanceof String)) {
                continue;
            }

            msg = replacer(msg, "(?i)" + entry.getKey(), entry.getValue().toString());
        }

        return msg;
    }

    private static String replacer(String msg, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);

        msg = sb.toString();

        return msg;
    }
}