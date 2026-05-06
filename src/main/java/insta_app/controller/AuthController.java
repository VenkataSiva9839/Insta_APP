package insta_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import insta_app.requestdto.UseReq;
import insta_app.requestdto.UserRequest;
import insta_app.responsedto.UseRes;
import insta_app.responsedto.UserRes;
import insta_app.service.AuthService;
import insta_app.utility.ResponseStructure;

@RestController
public class AuthController {

	private AuthService service;
	
	
	
	public AuthController(AuthService service) {
		this.service = service;
	}


	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<UserRes>> save(@RequestBody UserRequest user)
	{
		return service.save(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<UseRes>> login(@RequestBody UseReq user)
	{
		return service.login(user);
	
	}
	

}
