package com.apec.pos.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.RoleEntity;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.service.serviceInterface.AccountInterface;

@Service
public class AccountService extends BaseService<AccountRepository, AccountEntity, Integer> implements AccountInterface{

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	AccountRepository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public String login(AccountEntity accountEntity) {
		AccountEntity aEntity = accountRepository.findByUsername(accountEntity.getUsername());
		if (passwordEncoder.matches( accountEntity.getPassword(),aEntity.getPassword())) {
			return jwtService.generateToken(aEntity);
		}		
		return "đăng nhập không thành công";
	}

	@Override
	public String register(AccountEntity accountEntity) {
		// Kiểm tra xem tài khoản đã tồn tại chưa
	    if (accountRepository.findByUsername(accountEntity.getUsername()) != null) {	 
	        return null;
	    }

	    // Tài khoản chưa tồn tại, tạo một tài khoản mới với vai trò "USER"
	    Set<RoleEntity> roleEntity = new HashSet<>();
	    RoleEntity userRole = new RoleEntity();
	    userRole.setAuthority("USER");
	    userRole.setId(1);
	    roleEntity.add(userRole);

	    AccountEntity accountEntity2 = new AccountEntity();
	    accountEntity2.setAccountName(accountEntity.getAccountName());
	    accountEntity2.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
	    accountEntity2.setRoles(roleEntity);
	    accountEntity2.setUsername(accountEntity.getUsername());
	    // Lưu tài khoản mới vào cơ sở dữ liệu
	    accountRepository.insert(accountEntity2);
	    
	    return jwtService.generateToken(accountEntity2);
	}

}
