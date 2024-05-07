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
        // Đặt cấu hình cho ModelMapper sử dụng chiến lược khớp nghiêm ngặt
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Chuyển đổi từ MotelDTO sang MotelEntity sử dụng ModelMapper
        return modelMapper.map(motelDTO,MotelEntity.class);
    }

    public static AccountEntityDTO convertToAccountEntityDTO(AccountEntity accountEntity){
        // Chuyển đổi từ AccountEntity sang AccountEntityDTO sử dụng ModelMapper
        return modelMapper.map(accountEntity,AccountEntityDTO.class);
    }

    public static MotelDTO convertToMotelDTO(MotelEntity motelEntity){
        // Đặt cấu hình cho ModelMapper sử dụng chiến lược khớp nghiêm ngặt
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Chuyển đổi từ MotelEntity sang MotelDTO sử dụng ModelMapper
        MotelDTO motelDTO =modelMapper.map(motelEntity, MotelDTO.class);
        // Gán giá trị cho thuộc tính imageReturn của MotelDTO
        motelDTO.setImageReturn(motelEntity.getMotelImage());
        return motelDTO;
    }

    public static CityDTO convertToCityDTO(CityEntity cityEntity){
        // Chuyển đổi từ CityEntity sang CityDTO sử dụng ModelMapper
        return modelMapper.map(cityEntity, CityDTO.class);
    }

    public static List<CityDTO> convertToCityDTO(List<CityEntity> cityEntities){
        List<CityDTO> result = new ArrayList<>();
        // Duyệt qua từng phần tử trong danh sách CityEntity
        for (CityEntity x:cityEntities) {
            // Chuyển đổi từng CityEntity sang CityDTO và thêm vào danh sách kết quả
            result.add(convertToCityDTO(x));
        }
        return result;
    }

    public static  List<MotelDTO> convertToMotelDTO(List<MotelEntity> motelEntities){
        List<MotelDTO> result = new ArrayList<>();
        // Duyệt qua từng phần tử trong danh sách MotelEntity
        for (MotelEntity x:motelEntities) {
            // Chuyển đổi từng MotelEntity sang MotelDTO
            MotelDTO y =ConvertToDTO.convertToMotelDTO(x);
            // Thêm MotelDTO vào danh sách kết quả
            result.add(y);
        }
        return result;
    }
}
