package com.apec.pos;
import com.apec.pos.dto.MotelSearch;
import com.apec.pos.service.CityService;
import com.apec.pos.service.MotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "auth")
public class AuthController {

	@Autowired
	private CityService cityService;

	@Autowired
	private MotelService motelService;

	@RequestMapping(method = RequestMethod.GET,value = "/hello")
	public ResponseEntity hello(){
		return ResponseEntity.ok("hello");
	}

	@RequestMapping(method = RequestMethod.GET,value = "/hello1")
	public ResponseEntity hello1(){
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
}
