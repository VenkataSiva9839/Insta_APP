package insta_app.service;

import org.springframework.http.ResponseEntity;

import insta_app.requestdto.UseReq;
import insta_app.requestdto.UserRequest;
import insta_app.responsedto.UseRes;
import insta_app.responsedto.UserRes;
import insta_app.utility.ResponseStructure;

public interface AuthService {

	ResponseEntity<ResponseStructure<UserRes>> save(UserRequest user);

	ResponseEntity<ResponseStructure<UseRes>> login(UseReq user);


}
