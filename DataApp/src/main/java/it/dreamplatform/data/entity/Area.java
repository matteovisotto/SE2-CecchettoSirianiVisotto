package it.dreamplatform.data.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class represents the entity of an Area, mapped from the DB. The PRIMARY KEY, is the areaId that is also
 * autogenerated. The other element is the name of the area.
 */
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
