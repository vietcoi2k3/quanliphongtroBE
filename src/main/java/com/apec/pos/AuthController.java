package com.apec.pos;
import com.apec.pos.dto.AccountEntityDTO;
import com.apec.pos.dto.JwtResponse;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.service.AccountService;
import com.apec.pos.service.CityService;
import com.apec.pos.service.MotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private CityService cityService;

	@Autowired
	private MotelService motelService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET,value = "/hello")
	public ResponseEntity hello(){
		return ResponseEntity.ok("hello");
	}

	@RequestMapping(method = RequestMethod.GET,value = "/get-city-outstanding")
	public ResponseEntity getCityOutstanding(){
		return ResponseEntity.ok(cityService.getCityOutstanding());
	}


	@RequestMapping(method = RequestMethod.GET,value = "/get-motel-top")
	public ResponseEntity getMotelTop(){
		return ResponseEntity.ok(cityService.getMotelTop());
	}
	@RequestMapping(method =RequestMethod.POST ,value = "paing-motel")
	public ResponseEntity pagingMotel(@RequestBody MotelSearch motelSearch){
		return ResponseEntity.ok(motelService.pagingMotel(motelSearch));
	}

	@RequestMapping(method = RequestMethod.POST,value = "login")
	public ResponseEntity login(@RequestBody AccountEntityDTO accountEntityDTO){
		JwtResponse jwtResponse = accountService.login(accountEntityDTO);
		if (jwtResponse==null){
			return ResponseEntity.badRequest().body("Tài khoản hoặc mật khẩu không đúng");
		}
		return ResponseEntity.ok(accountService.login(accountEntityDTO));
	}

	@RequestMapping(method = RequestMethod.POST,value = "register")
	public ResponseEntity register(@RequestBody AccountEntityDTO accountEntityDTO){
		JwtResponse jwtResponse = accountService.register(accountEntityDTO);
		if (jwtResponse==null){
			return ResponseEntity.badRequest().body("Tài khoản đã tồn tại");
		}
		return ResponseEntity.ok(jwtResponse);
	}


	@RequestMapping(method = RequestMethod.GET,value = "get-motel-by-id")
	public ResponseEntity getDetailById(@RequestParam int id){
		return ResponseEntity.ok(motelService.getMotelById(id));
	}

}
