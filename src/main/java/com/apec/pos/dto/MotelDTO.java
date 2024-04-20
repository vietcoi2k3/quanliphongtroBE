package com.apec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MotelDTO {
    private String title;

    private String description;

    private float price;

    private Date dateRelease;

    private Date dateExpried;

    private String motelImage;

    private float acreage;

    private String address;
}
