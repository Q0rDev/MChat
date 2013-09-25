package ca.q0r.mchat.types;

import ca.q0r.mchat.util.MessageUtil;
import org.bukkit.command.CommandSender;

/**
 * Enum for Different <b>info.yml</b> Edit Types.
 */
public enum InfoEditType {
    /** Add Info Variable Command. */ ADD_INFO_VAR(5, "/%cmd add ivar <%Type> <Variable> [Value]"),
    /** Add Base Command. */ ADD_BASE(3, "/%cmd add %type <%Type>"),
    /** Add World Command. */ ADD_WORLD(4, "/%cmd add world <%Type> <World>"),
    /** Add World Variable Command. */ ADD_WORLD_VAR(6, "/%cmd add wvar <%Type> <World> <Variable> [Value]"),
    /** Remove Info Variable Command. */ REMOVE_INFO_VAR(4, "/%cmd remove ivar <%Type> <Variable>"),
    /** Remove Base Command. */ REMOVE_BASE(3, "/%cmd remove %type <%Type>"),
    /** Remove World Command. */ REMOVE_WORLD(4, "/%cmd remove world <%Type> <World>"),
    /** Remove World Variable Command. */ REMOVE_WORLD_VAR(5, "/%cmd remove wvar <%Type> <World> <Variable>"),
    /** Set Group Command. */ SET_GROUP(4, "/%cmd set group <Player> <Group>");

    private Integer i;
    private String msg;

    private InfoEditType(Integer i, String msg) {
        this.i = i;
        this.msg = msg;
    }

    /**
     * Command Length.
     * @return Length of Edit Command.
     */
    public Integer getLength() {
        return i;
    }

    /**
     * Command Message Sender.
     * @param sender Object Sending Message.
     * @param cmd Command being sent.
     * @param type Info Type being used.
     */
    public void sendMsg(CommandSender sender, String cmd, InfoType type) {
        String t = "player";
        String T = "Player";

        if (type == InfoType.GROUP) {
            t = "group";
            T =  "Group";
        }

        MessageUtil.sendMessage(sender, msg.replace("%cmd", cmd).replace("%Type", T).replace("%type", t));
    }
}
