package com.apec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MotelDTO {
    private long id;
    private String title;

    private String description;

    private float price;

    private MultipartFile motelImage;

    private String imageReturn;

    private float acreage;

    private String address;

    private long typeMotelID;

    private long cityEntityID;
}
