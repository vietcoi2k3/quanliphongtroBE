package com.apec.pos.service;
import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.MotelDetailDTO;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.MotelEntity;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.repository.MotelRepository;
import com.apec.pos.service.serviceInterface.MotelInterface;
import com.apec.pos.until.ConvertToDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
@Service
public class MotelService implements MotelInterface{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MotelRepository motelRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private JwtService jwtService;

    //phân trang
    @Override
    public ListMotelDTO pagingMotel(MotelSearch motelSearch) {
        ListMotelDTO listMotelDTO = new ListMotelDTO();
        // Khởi tạo một đối tượng ListMotelDTO mới.
        List<MotelEntity> motelEntities = motelRepository.pagingMotel(motelSearch);
        // Gọi phương thức pagingMotel từ motelRepository, truyền đối tượng MotelSearch làm tham số và lấy kết quả là một danh sách các MotelEntity.
        List<MotelDTO> motelDTOList = ConvertToDTO.convertToMotelDTO(motelEntities);
        // Gọi phương thức convertToMotelDTO từ lớp ConvertToDTO để chuyển đổi danh sách các MotelEntity thành danh sách các MotelDTO.
        listMotelDTO.setMotelDTOList(motelDTOList);
        // Đặt danh sách các MotelDTO vào thuộc tính MotelDTOList của đối tượng ListMotelDTO.
        listMotelDTO.setTotalMotel((int)motelRepository.countMotel(motelSearch));
        // Gọi phương thức countMotel từ motelRepository, truyền đối tượng MotelSearch làm tham số và lấy kết quả là số lượng motel.
        // Đặt số lượng motel vào thuộc tính TotalMotel của đối tượng ListMotelDTO.
        return listMotelDTO;
    }

    //lấy ra thông tin chi tiết của nhà trọ theo id
    @Override
    public MotelDetailDTO getMotelById(int id) {
        MotelEntity motelEntity = motelRepository.findOne(id);
        AccountEntity accountEntity = accountRepository.findOne((int)motelEntity.getAccountEntityID());
        MotelDetailDTO motelDetailDTO = MotelDetailDTO.builder()
                .accountName(accountEntity.getAccountName())
                .email(accountEntity.getEmail())
                .motelImage(motelEntity.getMotelImage())
                .phoneNumber(accountEntity.getPhoneNumber())
                .id(motelEntity.getId())
                .acreage(motelEntity.getAcreage())
                .address(motelEntity.getAddress())
                .title(motelEntity.getTitle())
                .typeMotel((int) motelEntity.getTypeMotelEntity().getId())
                .dateRelease(motelEntity.getDateRelease())
                .dateExpried(motelEntity.getDateExpried())
                .price(motelEntity.getPrice())
                .cityId(motelEntity.getCityEntityID())
                .description(motelEntity.getDescription())
                .build();
        return motelDetailDTO;
    }

    //thêm motel
    @Override
    public MotelDTO addMotel(MotelDTO motelDTO, HttpServletRequest httpServletRequest) throws IOException {
        AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
        if (accountEntity.getMoney()<15000){
            return null;
        }
        accountEntity.setMoney(accountEntity.getMoney()-15000);
        MotelEntity motelEntity = ConvertToDTO.convertToMotelEntity(motelDTO);
        motelEntity.setDateRelease(new Date());
        motelEntity.setAccountEntityID(accountEntity.getId());
        motelEntity.setMotelImage(fileUploadService.uploadFile(motelDTO.getMotelImage().getBytes()));
        motelEntity = motelRepository.insert(motelEntity);
        MotelDTO motelDTO1 = ConvertToDTO.convertToMotelDTO(motelEntity);
        motelDTO1.setImageReturn(motelEntity.getMotelImage());
        return motelDTO1;
    }


    //sửa  motel
    @Override
    public MotelDTO updateMotel(MotelDTO motelDTO ,HttpServletRequest httpServletRequest) throws IOException {
        Date dateEx = new Date();
        dateEx.setMonth(dateEx.getMonth()+1);
        AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
        MotelEntity motelEntity = motelRepository.findOne((int) motelDTO.getId());

        motelEntity.setAccountEntityID(accountEntity.getId());
        motelEntity.setAddress(motelDTO.getAddress());
        motelEntity.setAcreage(motelDTO.getAcreage());
        motelEntity.setCityEntityID(motelDTO.getCityEntityID());
        motelEntity.setPrice(motelDTO.getPrice());
        motelEntity.setDateExpried(dateEx);
        motelEntity.setDateRelease(new Date());
        motelEntity.setMotelImage(motelDTO.getMotelImage()!=null?fileUploadService.uploadFile(motelDTO.getMotelImage().getBytes()):motelEntity.getMotelImage());
        motelEntity.setTypeMotelID(motelDTO.getTypeMotelID());
        motelEntity.setDescription(motelDTO.getDescription());
        motelEntity.setTitle(motelDTO.getTitle());
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