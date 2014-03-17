package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.regionName;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.org.whoami.geoip.GeoIPLookup;

public class GeoIpVars {
    static GeoIPLookup geoip;

    public static void addVars(GeoIPLookup geo) {
        geoip = geo;

        VariableManager.addVars(new Var[]{new CountryName(), new CountryCode(), new Region(), new City()});
    }

    private static class CountryName extends Var {
        @Keys(keys = {"geoCountry"})
        public Object getValue(Object obj) {
            Player player = Bukkit.getPlayer(obj.toString());
            if (player != null) {
                return geoip.getLocation((player).getAddress().getAddress()).countryName;
            }

            return "";
        }
    }

    private static class CountryCode extends Var {
        @Keys(keys = {"geoCountryCode"})
        public Object getValue(Object obj) {
            Player player = Bukkit.getPlayer(obj.toString());
            if (player != null) {
                return geoip.getLocation((player).getAddress().getAddress()).countryCode;
            }

            return "";
        }
    }

    private static class Region extends Var {
        @Keys(keys = {"geoRegion"})
        public Object getValue(Object obj) {
            Player player = Bukkit.getPlayer(obj.toString());
            if (player != null) {
                Location location = geoip.getLocation((player).getAddress().getAddress());
                return regionName.regionNameByCode(location.countryCode, location.region);
            }

            return "";
        }
    }

    private static class City extends Var {
        @Keys(keys = {"geoCity"})
        public Object getValue(Object obj) {
            Player player = Bukkit.getPlayer(obj.toString());
            if (player != null) {
                return geoip.getLocation((player).getAddress().getAddress()).city;
            }

            return "";
        }
    }
}