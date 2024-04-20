package com.apec.pos.until;

import com.apec.pos.dto.CityDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.entity.CityEntity;
import com.apec.pos.entity.MotelEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class ConvertToDTO {
    public static ModelMapper modelMapper = new ModelMapper();
    public static List<CityDTO> convertToCityDTO(List<CityEntity> cityEntities){
        List<CityDTO> result = new ArrayList<>();
        for (CityEntity x:cityEntities
        ) {
            result.add(modelMapper.map(x, CityDTO.class));
        }
        return result;
    }

    public static  List<MotelDTO> convertToMotelDTO(List<MotelEntity> motelEntities){
        List<MotelDTO> result = new ArrayList<>();
        for (MotelEntity x:motelEntities
        ) {
            result.add(modelMapper.map(x, MotelDTO.class));
        }
        return result;
    }
}
