package com.example.UserRegistrationService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserRegistrationService.model.AuthRequest;
import com.example.UserRegistrationService.model.CustomResponse;
import com.example.UserRegistrationService.model.RecordNotFoundException;
import com.example.UserRegistrationService.model.User;
import com.example.UserRegistrationService.service.UserService;
import com.example.UserRegistrationService.util.JwtUtil;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/user/test")
    public String welcome() {
        return "Welcome to javatechie !!";
    }

    @PostMapping("/user/register")
    public ResponseEntity<CustomResponse> addUser(@RequestBody User user) 
    {
    	if(!userService.checkUserName(user)) 
    	{
    		return new ResponseEntity<CustomResponse>(
    				new CustomResponse("Already Registered with this Username",HttpStatus.OK),HttpStatus.FORBIDDEN);
    	}
    	if(!userService.checkEmail(user)) 
    	{
    		return new ResponseEntity<CustomResponse>(
    				new CustomResponse("Already Registered with this email",HttpStatus.OK),HttpStatus.FORBIDDEN);
    	}
    	return new ResponseEntity<CustomResponse>(
    			new CustomResponse((Object)userService.addUser(user),HttpStatus.OK), HttpStatus.OK);
    }
    
    @PostMapping("/user/authenticate")
    public ResponseEntity<CustomResponse> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
    	if(userService.checkUserName(authRequest.getUserName())) 
    	{
    		return new ResponseEntity<CustomResponse>(
    				new CustomResponse((Object)("No user with this username"),HttpStatus.OK),HttpStatus.OK);
    	}
    	try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            return new ResponseEntity<CustomResponse>(
            		new CustomResponse(new String("Invalid password"),HttpStatus.OK), HttpStatus.OK);
        }
        return new ResponseEntity<CustomResponse>(
        		new CustomResponse(jwtUtil.generateToken(authRequest.getUserName()),HttpStatus.OK), HttpStatus.OK);
    }
}
