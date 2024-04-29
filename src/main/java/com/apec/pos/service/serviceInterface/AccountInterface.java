package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.entity.AccountEntity;

public interface AccountInterface {

	public String login(AccountEntityDTO accountEntityDTO);
	
	public String register(AccountEntityDTO accountEntityDTO);
}
