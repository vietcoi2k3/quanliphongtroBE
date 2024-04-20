package com.apec.pos.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cityName;

    private String cityImage;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "cityEntity")
    @JsonManagedReference(value = "city-motel")
    private List<MotelEntity> motelEntities;
    
}
