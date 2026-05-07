package insta_app.serviceimpl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import insta_app.entity.User;
import insta_app.exception.UserAlreadyExistByEmail;
import insta_app.exception.UserAlreadyLogin;
import insta_app.exception.UserNotExistByEmail;
import insta_app.exception.UserPasswordNotExist;
import insta_app.exception.UserRoleNotValid;
import insta_app.repo.AuthRepo;
import insta_app.requestdto.UseReq;
import insta_app.requestdto.UserRequest;
import insta_app.responsedto.UseRes;
import insta_app.responsedto.UserRes;
import insta_app.service.AuthService;
import insta_app.utility.ResponseStructure;

@Service
public class AuthServiceImpl implements AuthService{

	private AuthRepo authrepo;
	private ResponseStructure<UserRes> structure;
	private ResponseStructure<UseRes> structureRes;

	

	public AuthServiceImpl(AuthRepo authrepo, ResponseStructure<UserRes> structure,
			ResponseStructure<UseRes> structureRes, ResponseStructure<User> structureOut) {
		this.authrepo = authrepo;
		this.structure = structure;
		this.structureRes = structureRes;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserRes>> save(UserRequest user) {

		Optional<User> check=authrepo.findByEmail(user.getEmail());

		if(check.isEmpty())
		{
			User save = authrepo.save(changeUserRequesttoUser(user, new User()));

			return ResponseEntity.ok(structure.setMessage("Data Saved Successfully").setStatuscode(HttpStatus.OK.value()).setBody(changeUsertoUserReq(save, new UserRes())));
		}

		throw new UserAlreadyExistByEmail("Email Already Exist");

	}

	private UserRes changeUsertoUserReq(User save, UserRes userRes) {

		userRes.setId(save.getId());

		return userRes;
	}

	private User changeUserRequesttoUser(UserRequest user, User use) {

		use.setEmail(user.getEmail());

		use.setName(user.getName());

		use.setPassword(user.getPassword());

		use.setRole(user.getRole());

		use.setLogin(false);

		return use;
	}



	@Override
	public ResponseEntity<ResponseStructure<UseRes>> login(UseReq user) {

		Optional<User> log=	authrepo.findByEmail(user.getEmail());

		if(!log.isEmpty())
		{
			User test = log.get();

			if(test.getPassword().equals(user.getPassword()))
			{
			if(	test.getRole().equals(user.getRole()))
				{
				if(!test.isLogin())
				{
				authrepo.updateLogStatus(test.getId());
					return ResponseEntity.ok(structureRes.setBody(changeTestToUseRes(test, new UseRes())).setStatuscode(HttpStatus.OK.value()).setMessage("Login successfully"));
				}
				else {
					throw new UserAlreadyLogin("Already Login");
				}
				}
			else
			{
				throw new UserRoleNotValid("Invalid Role Selected");
			}
			}
			else
			{	
				throw new UserPasswordNotExist("Incorrect Password");
			}
		}
		else
		{
			throw new UserNotExistByEmail("Invalid Email");
		}
	}

	private UseRes changeTestToUseRes(User test, UseRes useRes) {
		
		useRes.setId(test.getId());
		
		return useRes;
	}

	
}

