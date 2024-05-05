package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.JwtResponse;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.UserUpdateDTO;
import com.apec.pos.entity.AccountEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface AccountInterface {

	public JwtResponse login(AccountEntityDTO accountEntityDTO);
	
	public JwtResponse register(AccountEntityDTO accountEntityDTO);

	public AccountEntityDTO getAccount(HttpServletRequest httpServletRequest);

	public UserUpdateDTO updateAccount(UserUpdateDTO updateDTO, HttpServletRequest httpServletRequest) throws IOException;

	public String changePassword(String newPassword,String oldPassword,HttpServletRequest httpServletRequest);

	public List<MotelDTO> getMotelByUser(HttpServletRequest httpServletRequest, int pageIndex, int pageSize);

}
