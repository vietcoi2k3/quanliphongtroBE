package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.CityDTO;
import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.CityEntity;

import java.util.List;

public interface MotelInterface {

    public ListMotelDTO pagingMotel(MotelSearch motelSearch);

}
