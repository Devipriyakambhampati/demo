package com.demoapplication.demo;

import java.util.Objects;

public class Station {
    private String stationId;
    private String name;
    private boolean hdEnabled;
    private String callSign;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHdEnabled() {
        return hdEnabled;
    }

    public void setHdEnabled(boolean hdEnabled) {
        this.hdEnabled = hdEnabled;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationId='" + stationId + '\'' +
                ", name='" + name + '\'' +
                ", hdEnabled=" + hdEnabled +
                ", callSign='" + callSign + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return isHdEnabled() == station.isHdEnabled() &&
                Objects.equals(getStationId(), station.getStationId()) &&
                Objects.equals(getName(), station.getName()) &&
                Objects.equals(getCallSign(), station.getCallSign());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStationId(), getName(), isHdEnabled(), getCallSign());
    }


}
