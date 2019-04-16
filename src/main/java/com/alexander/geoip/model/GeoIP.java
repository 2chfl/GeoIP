package com.alexander.geoip.model;

import com.alexander.geoip.model.Parsing.*;

import java.util.Map;
import java.util.HashMap;

public class GeoIP {

    private Whois whois = new Whois();
    private Maxmind maxmind = new Maxmind();

    private static ParseIP parseIP = new ParseIP();
    private static ParseWhois parseWhois = new ParseWhois();

    public IPInfo getInfo(String dataString, boolean onlyRussia, boolean onlyHMAO, boolean onlyCity) {
        String ipAddress = getIPAddress(dataString);

        if (ipAddress == null) {
            return null;
        }

        Map<String, String> maxmindInfo = maxmind.getInfo(
                ipAddress,
                onlyRussia,
                onlyHMAO,
                onlyCity
        );

        if (maxmindInfo == null) {
            return null;
        }

        return new IPInfo(
                ipAddress,
                maxmindInfo.get("country"),
                maxmindInfo.get("region"),
                maxmindInfo.get("city")
        );
    }

    public Map<String, String> getWhois(String ipAddress) {
        Map<String, String> hashMap = new HashMap<>();

        String whoisResult = whois.execCommand(ipAddress);

        hashMap.put("whois", whoisResult);
        hashMap.put("network", getNetwork(whoisResult));
        hashMap.put("owner", getOwner(whoisResult));

        return hashMap;
    }

    private String getIPAddress(String dataString) {
        return parseIP.getIP(dataString);
    }

    private String getNetwork(String dataString) {
        return parseWhois.getNetwork(dataString);
    }

    private String getOwner(String dataString) {
        return parseWhois.getOwner(dataString);
    }

}
