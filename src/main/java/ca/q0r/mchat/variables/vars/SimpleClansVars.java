package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import com.p000ison.dev.simpleclans2.chat.SimpleClansChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SimpleClansVars {
    private static SimpleClansChat chat;

    public static void addVars(SimpleClansChat simpleClansChat) {
        chat = simpleClansChat;

        VariableManager.addVars(new Var[]{new ClanVar(), new RankVar()});
    }

    private static class ClanVar extends Var {
        @Keys(keys = {"-clan"})
        public String getValue(UUID uuid) {
            try {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    return chat.formatVariable("-clan",
                            chat.getClanPlayerManager().getClanPlayer(player).getClan().getTag());
                }
            } catch (Exception ignored) {
            }

            return "";
        }
    }

    private static class RankVar extends Var {
        @Keys(keys = {"-rank"})
        public String getValue(UUID uuid) {
            try {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    return chat.formatVariable("-rank",
                            chat.getClanPlayerManager().getClanPlayer(player).getRank().getTag());
                }
            } catch (Exception ignored) {
            }

            return "";
        }
    }
}