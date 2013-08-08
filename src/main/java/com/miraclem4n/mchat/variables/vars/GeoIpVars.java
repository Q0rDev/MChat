package com.miraclem4n.mchat.variables.vars;

import com.maxmind.geoip.Country;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.regionName;
import com.miraclem4n.mchat.variables.MultiVariable;
import com.miraclem4n.mchat.variables.Var;

public class GeoIpVars extends MultiVariable {
    public GeoIpVars(Country country, Location location) {
        addVar(new Var(new String[]{"geoCountry"}, country.getName()));
        addVar(new Var(new String[]{"geoCountryCode"}, country.getCode()));
        addVar(new Var(new String[]{"geoRegion"}, regionName.regionNameByCode(country.getCode(), location.region)));
        addVar(new Var(new String[]{"geoCity"}, location.city));
    }
}
