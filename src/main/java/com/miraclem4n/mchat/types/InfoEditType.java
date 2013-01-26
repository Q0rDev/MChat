package com.miraclem4n.mchat.types;

import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.CommandSender;

public enum InfoEditType {
    ADD_INFO_VAR(5, "/%cmd add ivar <%Type> <Variable> [Value]"),
    ADD_BASE(3, "/%cmd add %type <%Type>"),
    ADD_WORLD(4, "/%cmd add world <%Type> <World>"),
    ADD_WORLD_VAR(6, "/%cmd add wvar <%Type> <World> <Variable> [Value]"),
    REMOVE_INFO_VAR(4, "/%cmd remove ivar <%Type> <Variable>"),
    REMOVE_BASE(3, "/%cmd remove %type <%Type>"),
    REMOVE_WORLD(4, "/%cmd remove world <%Type> <World>"),
    REMOVE_WORLD_VAR(5, "/%cmd remove wvar <%Type> <World> <Variable>"),
    SET_GROUP(4, "/%cmd set group <Player> <Group>");

    private Integer i;
    private String msg;

    InfoEditType(Integer i, String msg) {
        this.i = i;
        this.msg = msg;
    }

    public Integer getLength() {
        return i;
    }

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
