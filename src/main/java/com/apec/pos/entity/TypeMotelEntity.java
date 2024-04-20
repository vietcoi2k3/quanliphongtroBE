package com.apec.pos.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TypeMotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameType;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "typeMotelEntity")
    @JsonManagedReference(value = "type-motel")
    private List<MotelEntity> motelEntities;
}
