package com.apec.pos.service;

import com.apec.pos.dto.CityDTO;
import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.CityEntity;
import com.apec.pos.entity.MotelEntity;
import com.apec.pos.repository.MotelRepository;
import com.apec.pos.service.serviceInterface.MotelInterface;
import com.apec.pos.until.ConvertToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotelService  implements  MotelInterface{

    @Autowired
    private MotelRepository motelRepository;

    @Override
    public ListMotelDTO pagingMotel(MotelSearch motelSearch) {
        ListMotelDTO listMotelDTO = new ListMotelDTO();
        List<MotelEntity> motelEntities = motelRepository.pagingMotel(motelSearch);
        List<MotelDTO> motelDTOList = ConvertToDTO.convertToMotelDTO(motelEntities);
        listMotelDTO.setMotelDTOList(motelDTOList);
        listMotelDTO.setTotalMotel((int)motelRepository.countMotel(motelSearch));
        return listMotelDTO;
    }
}
