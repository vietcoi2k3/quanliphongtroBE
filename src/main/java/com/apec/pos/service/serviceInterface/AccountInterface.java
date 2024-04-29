package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.JwtResponse;
import com.apec.pos.entity.AccountEntity;

public interface AccountInterface {

	public JwtResponse login(AccountEntityDTO accountEntityDTO);
	
	public JwtResponse register(AccountEntityDTO accountEntityDTO);
}
