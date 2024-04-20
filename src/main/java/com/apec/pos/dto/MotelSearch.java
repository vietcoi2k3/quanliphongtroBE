package com.apec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotelSearch {
    private int typeMotelId;
    private int cityEntityId;

    private float priceCeil;

    private float priceFloor;

    private float acreageCeil;

    private float acreageFloor;

    private int pageSize;

    private int pageIndex;
}
