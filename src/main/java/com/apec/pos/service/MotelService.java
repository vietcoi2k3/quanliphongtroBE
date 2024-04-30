package com.apec.pos.service;
import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.MotelEntity;
import com.apec.pos.repository.MotelRepository;
import com.apec.pos.service.serviceInterface.MotelInterface;
import com.apec.pos.until.ConvertToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
@Service
public class MotelService implements MotelInterface{

    @Autowired
    private MotelRepository motelRepository;

    @Autowired
    private FileUploadService fileUploadService;
    @Override
    public ListMotelDTO pagingMotel(MotelSearch motelSearch) {
        ListMotelDTO listMotelDTO = new ListMotelDTO();
        List<MotelEntity> motelEntities = motelRepository.pagingMotel(motelSearch);
        List<MotelDTO> motelDTOList = ConvertToDTO.convertToMotelDTO(motelEntities);
        listMotelDTO.setMotelDTOList(motelDTOList);
        listMotelDTO.setTotalMotel((int)motelRepository.countMotel(motelSearch));
        return listMotelDTO;
    }
    @Override
    public MotelDTO getMotelById(int id) {
        MotelEntity motelEntity = motelRepository.findOne(id);
        return ConvertToDTO.convertToMotelDTO(motelEntity);
    }
    @Override
    public MotelDTO addMotel(MotelDTO motelDTO) throws IOException {
        MotelEntity motelEntity = ConvertToDTO.convertToMotelEntity(motelDTO);
        motelEntity.setDateRelease(new Date());
        motelEntity.setMotelImage(fileUploadService.uploadFile(motelEntity.getMotelImage().getBytes()));
        motelEntity = motelRepository.insert(motelEntity);
        MotelDTO motelDTO1 = ConvertToDTO.convertToMotelDTO(motelEntity);
        motelDTO1.setImageReturn(motelEntity.getMotelImage());
        return motelDTO1;
    }

    @Override
    public MotelDTO updateMotel(MotelDTO motelDTO) throws IOException {
        MotelEntity motelEntity = motelRepository.findOne((int) motelDTO.getId());
        motelEntity.builder()
                .accountEntityID(motelDTO.getAccountEntityID())
                .motelImage(fileUploadService.uploadFile(motelEntity.getMotelImage().getBytes()))
                .address(motelDTO.getAddress())
                .acreage(motelDTO.getAcreage())
                .cityEntityID(motelDTO.getCityEntityID())
                .price(motelDTO.getPrice())
                .dateExpried(motelDTO.getDateExpried())
                .dateRelease(motelDTO.getDateRelease())
                .typeMotelID(motelDTO.getTypeMotelID())
                .description(motelDTO.getDescription())
                .title(motelDTO.getTitle())
                .build();
        motelEntity = motelRepository.update(motelEntity);
        MotelDTO motelDTO1 = ConvertToDTO.convertToMotelDTO(motelEntity);
        motelDTO1.setImageReturn(motelEntity.getMotelImage());
        return motelDTO1;
    }

    @Override
    public int deleteMotel(int id) throws IOException {
        return motelRepository.delete(id);
    }
}