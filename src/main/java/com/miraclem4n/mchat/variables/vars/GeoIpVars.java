package com.miraclem4n.mchat.variables.vars;

import com.maxmind.geoip.Country;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.regionName;
import com.miraclem4n.mchat.variables.VariableManager;

public class GeoIpVars {
    public static void addVars(VariableManager mgr, Country country, Location location) {
        mgr.addVars(new String[]{"geoCountry"}, country.getName());
        mgr.addVars(new String[]{"geoCountryCode"}, country.getCode());
        mgr.addVars(new String[]{"geoRegion"}, regionName.regionNameByCode(country.getCode(), location.region));
        mgr.addVars(new String[]{"geoCity"}, location.city);
    }
}
