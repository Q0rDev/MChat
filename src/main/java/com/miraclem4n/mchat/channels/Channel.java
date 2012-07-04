package com.miraclem4n.mchat.channels;

import com.miraclem4n.mchat.types.ChannelType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Channel {
    HashMap<String, Boolean> occupants;
    String name, prefix, suffix, password;
    ChannelType type;
    Integer distance;
    Boolean passworded, defaulted;

    public Channel(String name, ChannelType type, String prefix, String suffix, Boolean passworded, String password, Integer distance, Boolean defaulted) {
        this.name = name.toLowerCase();
        this.type = type;

        this.prefix = prefix;
        this.suffix = suffix;
        this.passworded = passworded;
        this.password = password;
        this.distance = distance;
        this.defaulted = defaulted;

        this.occupants = new HashMap<String, Boolean>();
    }

    public void setName(String name) {
        if (name == null)
            return;

        this.name = name.toLowerCase();
    }
    
    public String getName() {
        return name;
    }

    public void setType(ChannelType type) {
        if (type == null)
            type = ChannelType.GLOBAL;

        this.type = type;
    }

    public ChannelType getType() {
        return type;
    }

    public Set<String> getOccupants() {
        return occupants.keySet();
    }

    public Set<String> getActiveOccupants() {
        Set<String> set = new HashSet<String>();
        
        for (Map.Entry<String, Boolean> entry : occupants.entrySet())
            if (entry.getValue())
                set.add(entry.getKey());

        return set;
    }

    public void addOccupant(String occupant, Boolean state) {
        if (occupant == null || state == null)
            return;

        occupants.put(occupant, state);
    }

    public void removeOccupant(String occupant) {
        if (occupant == null || occupants.get(occupant) == null)
            return;

        occupants.remove(occupant);
    }

    public Boolean getOccupantAvailability(String occupant) {
        if (occupant == null || occupants.get(occupant) == null)
            return false;

        return occupants.get(occupant);
    }

    public void setOccupantAvailability(String occupant, Boolean state) {
        if (occupant == null || state == null || occupants.get(occupant) == null)
            return;

        occupants.put(occupant, state);
    }
    
    public void setPrefix(String prefix) {
        if (prefix == null)
            return;

        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        if (suffix == null)
            return;

        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
    
    public void setDistance(Integer distance) {
        if (distance == null)
            return;

        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }
    
    public Boolean isPassworded() {
        return passworded;
    }
    
    public void setPassworded(Boolean passworded, String password) {
        if (passworded == null || password == null)
            return;

        type = ChannelType.PASSWORD;

        this.passworded = passworded;
        this.password = password;
    }

    public void setPassword(String password) {
        if (password == null)
            return;

        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public Boolean isDefault() {
        return defaulted;
    }

    public void setDefault(Boolean defaulted) {
        if (defaulted == null)
            return;

        this.defaulted = defaulted;
    }

    public void sendMessageFrom(Player player, String message) {
        if (player == null || message == null)
            return;

        String msg = MessageUtil.addColour(prefix + name + suffix) + " " + message;

        for (String names : getActiveOccupants()) {
            Player playerz = Bukkit.getServer().getPlayer(names);

            if (playerz == null)
                continue;

            if (getType() == ChannelType.PASSWORD || getType() == ChannelType.PRIVATE) {
                playerz.sendMessage(msg);
            } else if (getType() == ChannelType.LOCAL) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName())
                        && playerz.getLocation().distance(player.getLocation()) > distance)
                    playerz.sendMessage(msg);
            } else if (getType() == ChannelType.WORLD) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName()))
                    playerz.sendMessage(msg);
            } else if (getType() == ChannelType.CHUNK) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName())
                        && playerz.getLocation().getChunk() == player.getLocation().getChunk())
                    playerz.sendMessage(msg);
            } else
                playerz.sendMessage(msg);
        }

        MessageUtil.log(msg);
    }

    public void broadcastMessage(String message) {
        if (message == null)
            return;

        String msg = MessageUtil.addColour(prefix + name + suffix + " " + message);

        for (String names : getActiveOccupants()) {
            Player playerz = Bukkit.getServer().getPlayer(names);

            if (playerz == null)
                continue;

            playerz.sendMessage(msg);
        }

        MessageUtil.log(msg);
    }
}
