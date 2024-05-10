package com.apec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotelReturn {
    private long id;
    private String title;
    private String description;
    private float price;
    private String motelImage;
    private float acreage;
    private String address;
    private long typeMotelID;
    private long cityEntityID;
}
