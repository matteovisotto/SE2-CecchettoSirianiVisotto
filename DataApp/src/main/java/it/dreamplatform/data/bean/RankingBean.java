package it.dreamplatform.data.bean;

import it.dreamplatform.data.utils.ZoneEnum;

import java.io.Serializable;
import java.sql.Timestamp;

public class RankingBean implements Serializable {
    private String district;
    private ZoneEnum zone;
    private int position;
    private double value;
    private Timestamp timestamp;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public ZoneEnum getZone() {
        return zone;
    }

    public void setZone(ZoneEnum zone) {
        this.zone = zone;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
