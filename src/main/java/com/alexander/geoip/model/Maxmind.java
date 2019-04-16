package com.alexander.geoip.model;

import com.alexander.geoip.model.Exception.GeoIPException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;

import java.io.File;
import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Map;
import java.util.HashMap;

public class Maxmind {

    private static final String maxmindPath = String.format("%s\\GeoIP2-City.mmdb", System.getProperty("user.dir"));

    private static DatabaseReader databaseReader;

    public Maxmind() {
        initDataBase();
    }

    public Map<String, String> getInfo(String ipAddress, boolean onlyRussia, boolean onlyHMAO, boolean onlyCity) {
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (inetAddress != null) {
            CityResponse cityResponse = getCityResponse(inetAddress);

            if (cityResponse != null) {
                Country country = getCountry(cityResponse);

                if (country != null) {
                    String countryCode = getCountryCode(country);

                    if (countryCode != null) {
                        if (onlyRussia && !countryCode.equals("RU")) {
                            return null;
                        }

                        String regionName = getRegionName(
                                getRegion(cityResponse)
                        );

                        String cityName = getCityName(
                                getCity(cityResponse)
                        );

                        if (regionName != null) {
                            if (onlyHMAO && !regionName.equals("Khanty-Mansia")) {
                                return null;
                            }

                            if (onlyCity && cityName == null) {
                                return null;
                            }

                            Map<String, String> hashMap = new HashMap<>();

                            hashMap.put("country", getCountryName(country));
                            hashMap.put("region", regionName);
                            hashMap.put("city", cityName);

                            return hashMap;
                        }
                    }
                }
            }
        }

        return null;
    }

    private void initDataBase() {
        File dataBase = new File(maxmindPath);

        try {
            databaseReader = new DatabaseReader.Builder(dataBase).build();
        } catch (IOException e) {
            new GeoIPException(e.getMessage());
        }
    }

    private CityResponse getCityResponse(InetAddress inetAddress) {
        try {
            return databaseReader.city(inetAddress);
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Country getCountry(CityResponse cityResponse) {
        return cityResponse.getCountry();
    }

    private String getCountryName(Country country) {
        return country.getName();
    }

    private String getCountryCode(Country country) {
        return country.getIsoCode();
    }

    private Subdivision getRegion(CityResponse cityResponse) {
        return cityResponse.getMostSpecificSubdivision();
    }

    private String getRegionName(Subdivision subdivision) {
        return subdivision.getName();
    }

    private City getCity(CityResponse cityResponse) {
        return cityResponse.getCity();
    }

    private String getCityName(City city) {
        return city.getName();
    }

}
