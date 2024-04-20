package com.apec.pos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class MotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private float price;

    private Date dateRelease;

    private Date dateExpried;

    private String motelImage;

    private float acreage;

    private String address;

    @Column(name = "accountEntityID")
    private long accountEntityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "account-motel")
    @JoinColumn(name = "accountEntityID",updatable = false,insertable = false)
    private AccountEntity accountEntity;

    @Column(name = "typeMotelID")
    private long typeMotelID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "type-motel")
    @JoinColumn(name = "typeMotelID",updatable = false,insertable = false)
    private TypeMotelEntity typeMotelEntity;

    @Column(name = "cityEntityID")
    private long cityEntityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "city-motel")
    @JoinColumn(name = "cityEntityID",updatable = false,insertable = false)
    private CityEntity cityEntity;

}
