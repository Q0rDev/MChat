package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DebugListener implements Listener {
    public DebugListener() {}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        MessageUtil.sendMessage(player, "Command: " + "Invite");
        Bukkit.getServer().dispatchCommand(player, "pmchatinvite MiracleM");
        MessageUtil.sendMessage(player, "Command: " + "Deny");
        Bukkit.getServer().dispatchCommand(player, "pmchatdeny");
        MessageUtil.sendMessage(player, "Command: " + "Invite");
        Bukkit.getServer().dispatchCommand(player, "pmchatinvite MiracleM");
        MessageUtil.sendMessage(player, "Command: " + "Accept");
        Bukkit.getServer().dispatchCommand(player, "pmchataccept");
        MessageUtil.sendMessage(player, "Command: " + "Leave");
        Bukkit.getServer().dispatchCommand(player, "pmchatleave");
        MessageUtil.sendMessage(player, "Command: " + "Say");
        Bukkit.getServer().dispatchCommand(player, "mchatsay Noep.zml");
        MessageUtil.sendMessage(player, "Command: " + "Shout");
        Bukkit.getServer().dispatchCommand(player, "mchatshout Noep.zml");
        MessageUtil.sendMessage(player, "Command: " + "Me");
        Bukkit.getServer().dispatchCommand(player, "mchatme Noep.zml");
        MessageUtil.sendMessage(player, "Command: " + "List");
        Bukkit.getServer().dispatchCommand(player, "mchatlist");
        MessageUtil.sendMessage(player, "Command: " + "AFK");
        Bukkit.getServer().dispatchCommand(player, "mchatafk");
        MessageUtil.sendMessage(player, "Command: " + "UnAFK");
        Bukkit.getServer().dispatchCommand(player, "mchatafk");
        MessageUtil.sendMessage(player, "Command: " + "AFK Other");
        Bukkit.getServer().dispatchCommand(player, "mchatafkother Mira");
        MessageUtil.sendMessage(player, "Command: " + "UnAFK Other");
        Bukkit.getServer().dispatchCommand(player, "mchatafkother Mira");
        MessageUtil.sendMessage(player, "Command: " + "Message Prefix Set");
        Bukkit.getServer().dispatchCommand(player, "mchatmessageprefix set Noep");
        MessageUtil.sendMessage(player, "Command: " + "Message Prefix Remove");
        Bukkit.getServer().dispatchCommand(player, "mchatmessageprefix remove");
        MessageUtil.sendMessage(player, "Command: " + "Mute");
        Bukkit.getServer().dispatchCommand(player, "mchatmute Miracle");
        MessageUtil.sendMessage(player, "Command: " + "UnMute");
        Bukkit.getServer().dispatchCommand(player, "mchatmute Miracle");
        MessageUtil.sendMessage(player, "Command: " + "PM");
        Bukkit.getServer().dispatchCommand(player, "pmchat Mira Noep");
        MessageUtil.sendMessage(player, "Command: " + "Reply");
        Bukkit.getServer().dispatchCommand(player, "pmreply Noep");
        MessageUtil.sendMessage(player, "Command: " + "Who");
        Bukkit.getServer().dispatchCommand(player, "mchatwho Mira");
        MessageUtil.sendMessage(player, "Command: " + "Version");
        Bukkit.getServer().dispatchCommand(player, "mchat version");
        MessageUtil.sendMessage(player, "Command: " + "Reload *");
        Bukkit.getServer().dispatchCommand(player, "mchat reload all");
        Bukkit.getServer().dispatchCommand(player, "mchat reload config");
        Bukkit.getServer().dispatchCommand(player, "mchat reload info");
        Bukkit.getServer().dispatchCommand(player, "mchat reload locale");
        Bukkit.getServer().dispatchCommand(player, "mchat reload censor");
        MessageUtil.sendMessage(player, "Command: " + "Info Related");
        Bukkit.getServer().dispatchCommand(player, "mchatuser add player MiracleM4n Adminz0rz");
        Bukkit.getServer().dispatchCommand(player, "mchatuser add ivar MiracleM4n prefix Noepzml");
        Bukkit.getServer().dispatchCommand(player, "mchatuser add world MiracleM4n DtK");
        Bukkit.getServer().dispatchCommand(player, "mchatuser add wvar MiracleM4n DtK prefix Noepxml");
        Bukkit.getServer().dispatchCommand(player, "mchatuser remove wvar MiracleM4n DtK prefix");
        Bukkit.getServer().dispatchCommand(player, "mchatuser remove world MiracleM4n DtK");
        Bukkit.getServer().dispatchCommand(player, "mchatuser remove ivar MiracleM4n prefix");
        Bukkit.getServer().dispatchCommand(player, "mchatuser remove player MiracleM4n");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup remove group Adminz0rz");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup add group Adminz0rz");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup add ivar Adminz0rz prefix Noepzml");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup add world Adminz0rz DtK");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup add wvar Adminz0rz DtK prefix Noepzml");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup remove wvar Adminz0rz DtK prefix");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup remove world Adminz0rz DtK");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup remove ivar Adminz0rz prefix");
        Bukkit.getServer().dispatchCommand(player, "mchatgroup remove group Adminz0rz");
    }
}
