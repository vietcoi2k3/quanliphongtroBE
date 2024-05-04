package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.ListMotelDTO;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.MotelSearch;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface MotelInterface {

    public ListMotelDTO pagingMotel(MotelSearch motelSearch);

    public MotelDTO getMotelById(int id);

    public MotelDTO addMotel(MotelDTO motelDTO, HttpServletRequest httpServletRequest) throws IOException;

    public MotelDTO updateMotel(MotelDTO motelDTO,HttpServletRequest httpServletRequest) throws IOException;

    public int deleteMotel(int id) throws IOException;
}
