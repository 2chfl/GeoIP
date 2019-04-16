package com.alexander.geoip.model;

public class IPInfo {

    private int id;
    private String ipAddress;
    private String country;
    private String region;
    private String city;
    private String network;
    private String owner;
    private String whois;
    private String other;

    private boolean isWhois;

    public IPInfo(String ipAddress, String country, String region, String city) {
        this.ipAddress = ipAddress;
        this.country = country;
        this.region = region;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWhois() {
        return whois;
    }

    public void setWhois(String whois) {
        this.whois = whois;
    }

    public boolean isWhois() {
         return isWhois;
    }

    public void setIsWhois(boolean whois) {
        this.isWhois = whois;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
