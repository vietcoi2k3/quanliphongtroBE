package com.apec.pos.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.JwtResponse;
import com.apec.pos.dto.UserUpdateDTO;
import com.apec.pos.until.ConvertToDTO;
import jakarta.servlet.http.HttpServletRequest;
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

	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	AccountRepository getRepository() {
		return null;
	}
	
	

	@Override
	public JwtResponse login(AccountEntityDTO accountEntityDTO) {
		AccountEntity aEntity = accountRepository.findByUsername(accountEntityDTO.getUsername());
		if (passwordEncoder.matches( accountEntityDTO.getPassword(),aEntity.getPassword())) {
			JwtResponse jwtResponse = new JwtResponse(
					jwtService.generateToken(aEntity),
					aEntity.getId(),
					aEntity.getUsername(),
					aEntity.getEmail(),
					aEntity.getPhoneNumber(),
					aEntity.getAccountName(),
					aEntity.getImageUser(),
					aEntity.getRoles());
			return jwtResponse;
		}		
		return null;
	}

	@Override
	public JwtResponse register(AccountEntityDTO accountEntityDTO) {
		// Kiểm tra xem tài khoản đã tồn tại chưa
	    if (accountRepository.findByUsername(accountEntityDTO.getUsername()) != null) {
	        return null;
	    }

	    // Tài khoản chưa tồn tại, tạo một tài khoản mới với vai trò "USER"
	    Set<RoleEntity> roleEntity = new HashSet<>();
	    RoleEntity userRole = new RoleEntity();
	    userRole.setAuthority("USER");
	    userRole.setId(1);
	    roleEntity.add(userRole);

	    AccountEntity accountEntity2 = new AccountEntity();
	    accountEntity2.setAccountName(accountEntityDTO.getAccountName());
	    accountEntity2.setPassword(passwordEncoder.encode(accountEntityDTO.getPassword()));
		accountEntity2.setPhoneNumber(accountEntityDTO.getPhoneNumber());
		accountEntity2.setEmail(accountEntityDTO.getEmail());
	    accountEntity2.setRoles(roleEntity);
	    accountEntity2.setUsername(accountEntityDTO.getUsername());
	    // Lưu tài khoản mới vào cơ sở dữ liệu
		accountEntity2= accountRepository.insert(accountEntity2);

		JwtResponse jwtResponse = new JwtResponse(
				jwtService.generateToken(accountEntity2),
				accountEntity2.getId(),
				accountEntity2.getUsername(),
				accountEntity2.getEmail(),
				accountEntity2.getPhoneNumber(),
				accountEntity2.getAccountName(),
				accountEntity2.getImageUser(),
				accountEntity2.getRoles());
	    return jwtResponse;
	}

	@Override
	public AccountEntityDTO getAccount(HttpServletRequest httpServletRequest) {
	    AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		return ConvertToDTO.convertToAccountEntityDTO(accountEntity);
	}

	@Override
	public UserUpdateDTO updateAccount(UserUpdateDTO accountEntityDTO, HttpServletRequest httpServletRequest) throws IOException {
		AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		accountEntity.setAccountName(accountEntityDTO.getAccountName());
		accountEntity.setEmail(accountEntityDTO.getEmail());
		accountEntity.setImageUser(fileUploadService.uploadFile(accountEntityDTO.getImg().getBytes()));
		accountEntity.setPhoneNumber(accountEntityDTO.getPhoneNumber());

		accountEntity= accountRepository.update(accountEntity);
		UserUpdateDTO updateDTO = new UserUpdateDTO();
		updateDTO.setAccountName(accountEntity.getAccountName());
		updateDTO.setPhoneNumber(accountEntity.getPhoneNumber());
		updateDTO.setEmail(accountEntity.getEmail());
		updateDTO.setImgReturn(accountEntity.getImageUser());
		return updateDTO;
	}
}
