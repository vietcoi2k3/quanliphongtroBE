package com.apec.pos.service.serviceInterface;

import com.apec.pos.entity.AccountEntity;

public interface AccountInterface {

	public String login(AccountEntity accountEntity);
	
	public String register(AccountEntity accountEntity);
}
