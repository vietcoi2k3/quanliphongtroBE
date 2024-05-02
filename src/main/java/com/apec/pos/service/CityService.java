package com.apec.pos.service;

import com.apec.pos.dto.CityDTO;
import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.CityEntity;
import com.apec.pos.entity.MotelEntity;
import com.apec.pos.repository.CityRepository;
import com.apec.pos.repository.MotelRepository;
import com.apec.pos.service.serviceInterface.CityInterface;
import com.apec.pos.until.ConvertToDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService implements CityInterface {

    @Autowired
    private MotelRepository motelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<CityDTO> getCityOutstanding() {
        return ConvertToDTO.convertToCityDTO(cityRepository.findAll());
    }

    public List<MotelDTO> getMotelTop(){
        return ConvertToDTO.convertToMotelDTO(motelRepository.getMotelTop());
    }

}
