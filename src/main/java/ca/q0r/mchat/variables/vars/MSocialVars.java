package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.configs.config.ConfigType;
import ca.q0r.mchat.configs.locale.LocaleType;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.variables.ResolvePriority;
import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import org.bukkit.entity.Player;

public class MSocialVars {
    private static Boolean mSocialB;

    public static void addVars(Boolean mSocial) {
        mSocialB = mSocial;

        VariableManager.addVar(new DistanceType());
    }

    private static class DistanceType extends Var {
        @Keys( keys = {"distancetype","dtype"} )
        @Meta( type = IndicatorType.CUS_VAR,
                priority = ResolvePriority.NORMAL )
        public Object getValue(Object obj) {
            String dType = "";

            if (obj != null && obj instanceof Player) {
                Player player = (Player) obj;
                String pName = player.getName();

                if (mSocialB
                        && API.isShouting().get(pName) != null
                        && API.isShouting().get(pName)) {
                    dType = API.getShoutFormat();
                } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
                    dType = LocaleType.FORMAT_LOCAL.getVal();
                }
            }

            return dType;
        }
    }
}
