package it.dreamplatform.data.dataapp.bean;

import com.esri.core.geometry.Polygon;

public class DistrictBean {
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
