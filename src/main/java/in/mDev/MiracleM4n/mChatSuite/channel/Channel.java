package in.mDev.MiracleM4n.mChatSuite.channel;

import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

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
            return;

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
        
        for (Map.Entry entry : occupants.entrySet())
            if ((Boolean)entry.getValue())
                set.add(entry.getKey().toString());

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
    
    public void setPassworded(String password) {
        if (password == null)
            return;

        type = ChannelType.PASSWORD;

        passworded = true;
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

    public void broadcastToChannel(Player player, String message) {
        if (player == null || message == null)
            return;

        String msg = Messanger.addColour(prefix + suffix) + message;

        for (String names : occupants.keySet()) {
            Player playerz = Bukkit.getServer().getPlayer(names);

            if (playerz == null)
                continue;

            if (type == ChannelType.PASSWORD || type == ChannelType.PRIVATE) {
                playerz.sendMessage(msg);
            } else if (type == ChannelType.LOCAL) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName())
                        && playerz.getLocation().distance(player.getLocation()) > distance)
                    playerz.sendMessage(msg);
            } else if (type == ChannelType.WORLD) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName()))
                    playerz.sendMessage(msg);
            } else if (type == ChannelType.CHUNK) {
                if (playerz.getWorld().getName().equals(player.getWorld().getName())
                        && playerz.getLocation().getChunk() == player.getLocation().getChunk())
                    playerz.sendMessage(msg);
            }
        }

        Messanger.log(msg);
    }
}
