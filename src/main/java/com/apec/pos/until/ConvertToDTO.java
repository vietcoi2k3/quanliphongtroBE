package com.apec.pos.until;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.CityDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.CityEntity;
import com.apec.pos.entity.MotelEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

public class ConvertToDTO {
    public static ModelMapper modelMapper = new ModelMapper();

    public static MotelEntity convertToMotelEntity(MotelDTO motelDTO){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(motelDTO,MotelEntity.class);
    }
    public static AccountEntityDTO convertToAccountEntityDTO(AccountEntity accountEntity){
        return modelMapper.map(accountEntity,AccountEntityDTO.class);
    }
    public static MotelDTO convertToMotelDTO(MotelEntity motelEntity){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(motelEntity, MotelDTO.class);
    }

    public static CityDTO convertToCityDTO(CityEntity cityEntity){
        return modelMapper.map(cityEntity, CityDTO.class);
    }
    public static List<CityDTO> convertToCityDTO(List<CityEntity> cityEntities){
        List<CityDTO> result = new ArrayList<>();
        for (CityEntity x:cityEntities
        ) {
            result.add(convertToCityDTO(x));
        }
        return result;
    }

    public static  List<MotelDTO> convertToMotelDTO(List<MotelEntity> motelEntities){
        List<MotelDTO> result = new ArrayList<>();
        for (MotelEntity x:motelEntities
        ) {
            MotelDTO y =ConvertToDTO.convertToMotelDTO(x);
            y.setImageReturn(x.getMotelImage());
            result.add(y);
        }
        return result;
    }
}
