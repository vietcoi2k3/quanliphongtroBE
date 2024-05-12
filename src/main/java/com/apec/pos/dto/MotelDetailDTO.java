package com.apec.pos.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MotelDetailDTO {
    private long id;

    private String title;

    private String description;

    private float price;

    private Date dateRelease;

    private Date dateExpried;

    private String motelImage;

    private float acreage;

    private long cityId;

    private int typeMotel;
    private String address;
    private String accountName;
    private String phoneNumber;
    private String email;
}
