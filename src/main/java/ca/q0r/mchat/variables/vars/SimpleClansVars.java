package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import com.p000ison.dev.simpleclans2.chat.SimpleClansChat;
import org.bukkit.entity.Player;

public class SimpleClansVars {
    private static SimpleClansChat chat;

    public static void addVars(SimpleClansChat simpleClansChat) {
        chat = simpleClansChat;

        VariableManager.addVars(new Var[]{new ClanVar(), new RankVar()});
    }

    private static class ClanVar extends Var {
        @Keys ( keys = {"-clan"} )
        public Object getValue(Object obj) {
            try {
                if (obj != null && obj instanceof Player) {
                    return chat.formatVariable("-clan",
                            chat.getClanPlayerManager().getClanPlayer((Player)obj).getClan().getTag());
                }
            } catch (Exception ignored) { }

            return "";
        }
    }

    private static class RankVar extends Var {
        @Keys ( keys = {"-rank"} )
        public Object getValue(Object obj) {
            try {
                if (obj != null && obj instanceof Player) {
                    return chat.formatVariable("-rank",
                            chat.getClanPlayerManager().getClanPlayer((Player)obj).getRank().getTag());
                }
            } catch (Exception ignored) { }

            return "";
        }
    }
}
