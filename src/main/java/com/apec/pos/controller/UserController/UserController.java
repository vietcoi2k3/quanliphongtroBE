package com.apec.pos.controller.UserController;

import com.apec.pos.dto.MotelDTO;
import com.apec.pos.dto.UserUpdateDTO;
import com.apec.pos.service.AccountService;
import com.apec.pos.service.MotelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/user")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MotelService motelService;
    @RequestMapping(method = RequestMethod.GET,value = "get-user")
    public ResponseEntity getUserById(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(accountService.getAccount(httpServletRequest));
    }
    @RequestMapping(method = RequestMethod.POST,value = "add-motel",consumes = "multipart/form-data")
    public ResponseEntity addMotel(@ModelAttribute MotelDTO motelDTO,HttpServletRequest httpServletRequest) throws IOException {
        return ResponseEntity.ok(motelService.addMotel(motelDTO,httpServletRequest));
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT,value = "update-motel",consumes = "multipart/form-data")
    public ResponseEntity updateMotel(@ModelAttribute MotelDTO motelDTO ,HttpServletRequest httpServletRequest) throws IOException {
        return ResponseEntity.ok(motelService.updateMotel(motelDTO, httpServletRequest));
    }
    @RequestMapping(method = RequestMethod.DELETE,value = "delete-motel")
    public ResponseEntity deleteMotel(@RequestParam int id) throws IOException {
        return ResponseEntity.ok(motelService.deleteMotel(id));
    }
    @RequestMapping(method = RequestMethod.PUT,value = "update-user",consumes = "multipart/form-data")
    public ResponseEntity updateUser(@ModelAttribute UserUpdateDTO updateDTO,HttpServletRequest httpServletRequest) throws IOException {
        return ResponseEntity.ok(accountService.updateAccount(updateDTO,httpServletRequest));
    }
}