package it.dreamplatform.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Area")
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String areaId;
    private String name;



    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
