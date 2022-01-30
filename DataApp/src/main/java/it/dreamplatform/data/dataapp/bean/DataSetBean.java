package it.dreamplatform.data.dataapp.bean;

import it.dreamplatform.data.dataapp.utils.ZoneEnum;

import java.sql.Timestamp;

public class DataSetBean {

    private Long dataSetId;
    private Long dataSourceId;
    private String district;
    private double longitude;
    private double latitude;
    private double valueNE;
    private double valueNW;
    private double valueSE;
    private double valueSW;

    public Long getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(Long dataSetId) {
        this.dataSetId = dataSetId;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getValueNE() {
        return valueNE;
    }

    public void setValueNE(double valueNE) {
        this.valueNE = valueNE;
    }

    public double getValueNW() {
        return valueNW;
    }

    public void setValueNW(double valueNW) {
        this.valueNW = valueNW;
    }

    public double getValueSE() {
        return valueSE;
    }

    public void setValueSE(double valueSE) {
        this.valueSE = valueSE;
    }

    public double getValueSW() {
        return valueSW;
    }

    public void setValueSW(double valueSW) {
        this.valueSW = valueSW;
    }
}
