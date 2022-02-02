package it.dreamplatform.data.bean;

import com.esri.core.geometry.Polygon;

import java.io.Serializable;

public class DistrictBean implements Serializable {
    private String name;
    private Polygon polygon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
