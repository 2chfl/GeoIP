package com.alexander.geoip.model.Parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseWhois {

    private static final String PROVIDER_NETWORK = "Network Name:[ ]+(([^\\r\\n])+)";
    private static final String PROVIDER_OWNER = "Owner Name:[ ]+(([^\\r\\n])+)";

    private static final Pattern networkPattern = Pattern.compile(PROVIDER_NETWORK);
    private static final Pattern ownerPattern = Pattern.compile(PROVIDER_OWNER);

    public String getNetwork(String dataString) {
        Matcher networkMatcher = networkPattern.matcher(dataString);

        if (networkMatcher.find()) {
            return networkMatcher.group(1);
        }

        return null;
    }

    public String getOwner(String dataString) {
        Matcher ownerMatcher = ownerPattern.matcher(dataString);

        if (ownerMatcher.find()) {
            return ownerMatcher.group(1);
        }

        return null;
    }

}
