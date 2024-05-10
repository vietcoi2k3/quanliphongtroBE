package com.apec.pos.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.JwtResponse;
import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.UserUpdateDTO;
import com.apec.pos.entity.MotelEntity;
import com.apec.pos.repository.MotelRepository;
import com.apec.pos.until.ConvertToDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.RoleEntity;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.service.serviceInterface.AccountInterface;

@Service
public class AccountService extends BaseService<AccountRepository, AccountEntity, Integer> implements AccountInterface{

	@Value("${jwt.secret}")
	private String secret;
	public static final long JWT_TOKEN_VALIDITY = 60;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	AccountRepository getRepository() {
		return null;
	}

	@Autowired
	MotelRepository motelRepository;
	

	@Override
	public JwtResponse login(AccountEntityDTO accountEntityDTO) {
		AccountEntity aEntity = accountRepository.findByUsername(accountEntityDTO.getUsername());
		if (passwordEncoder.matches( accountEntityDTO.getPassword(),aEntity.getPassword())) {
			JwtResponse jwtResponse = new JwtResponse(
					jwtService.generateToken(aEntity),
                    (long) aEntity.getId(),
					aEntity.getUsername(),
					aEntity.getEmail(),
					aEntity.getPhoneNumber(),
					aEntity.getAccountName(),
					aEntity.getImageUser(),
					aEntity.getRoles()
					,aEntity.getMoney()
			)
					;
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
                (long) accountEntity2.getId(),
				accountEntity2.getUsername(),
				accountEntity2.getEmail(),
				accountEntity2.getPhoneNumber(),
				accountEntity2.getAccountName(),
				accountEntity2.getImageUser(),
				accountEntity2.getRoles(),
				accountEntity2.getMoney());
	    return jwtResponse;
	}

	//lấy ra thông tin user để hiển thị
	@Override
	public AccountEntityDTO getAccount(HttpServletRequest httpServletRequest) {
	    AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		return ConvertToDTO.convertToAccountEntityDTO(accountEntity);
	}

	@Override
	public UserUpdateDTO updateAccount(UserUpdateDTO accountEntityDTO, HttpServletRequest httpServletRequest) throws IOException {
		//lấy ra user cần update
		if (accountEntityDTO.getImg()==null){
			System.out.println("tôi là việt");
		}
		AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		accountEntity.setAccountName(accountEntityDTO.getAccountName());
		accountEntity.setEmail(accountEntityDTO.getEmail());
		accountEntity.setImageUser(accountEntityDTO.getImg()!=null?fileUploadService.uploadFile(accountEntityDTO.getImg().getBytes()):accountEntity.getImageUser());

		accountEntity.setPhoneNumber(accountEntityDTO.getPhoneNumber());

		//update user
		accountEntity= accountRepository.update(accountEntity);
		UserUpdateDTO updateDTO = new UserUpdateDTO();
		updateDTO.setAccountName(accountEntity.getAccountName());
		updateDTO.setPhoneNumber(accountEntity.getPhoneNumber());
		updateDTO.setEmail(accountEntity.getEmail());
		updateDTO.setImgReturn(accountEntity.getImageUser());
		return updateDTO;
	}

	//thay đổi password
	@Override
	public String changePassword(String newPassword,String oldPassword,HttpServletRequest httpServletRequest) {
		AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		//so sanh pass cũ và mới
		if (passwordEncoder.matches(oldPassword,accountEntity.getPassword())){
			accountEntity.setPassword(passwordEncoder.encode(newPassword));
			accountRepository.update(accountEntity);
			return "Đổi mật khẩu thành công";
		}
		return null;
	}

	//lấy ra những nhà trọ thuộc user dựa vào token
	@Override
	public List<MotelDTO> getMotelByUser(HttpServletRequest httpServletRequest,int pageIndex,int pageSize) {
		//lấy ra user bằng việc decode token
		AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
		List<MotelEntity> motelEntities = motelRepository.getMotelByUserId((int) accountEntity.getId(),pageIndex,pageSize);
		return ConvertToDTO.convertToMotelDTO(motelEntities);
	}


	//gửi email
	public String sendEmail(String username){
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		String toEmail = accountEntity.getEmail();
		if (toEmail==null){
			return null;
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("tlufood.career@gmail.com");
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setSubject("Xác thực email");

		//tạo token ngẫu nhiên cho việc đổi mật khẩu
		simpleMailMessage.setText("Bấm vào đây để đổi mk "+"http://14.225.204.101:8080/auth/reset-pass?token="+this.generateToken(accountEntity)+"\n link hết hạn sau 2 phút");
		javaMailSender.send(simpleMailMessage);
		return "mời bạn check mail";
	}

	//tạo password ngẫu nhiên sau khi người dùng bấm đường link gửi về ở email
	public String resetPassword(String token){
		AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromToken(token));
		if (accountEntity!=null && jwtService.validateToken(token,accountEntity)){
			String password = this.generatePassword();
			accountEntity.setPassword(passwordEncoder.encode(password));
			accountRepository.update(accountEntity);
		}
		return null;
	}


	//hàm tạo token ngẫu nhiên
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	//hàm set các thuộc tính của token
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000*2))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	//hàm tạo password ngẫu nhiên
	public  String generatePassword() {
		StringBuilder password = new StringBuilder();
		SecureRandom random = new SecureRandom();
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
		int PASSWORD_LENGTH = 12;
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(randomIndex));
		}
		return password.toString();
	}
}
