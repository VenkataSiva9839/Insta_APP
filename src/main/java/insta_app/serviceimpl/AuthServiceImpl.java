package insta_app.serviceimpl;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import insta_app.cache.CacheStore;
import insta_app.entity.User;
import insta_app.exception.InvalidEmailSpefied;
import insta_app.exception.OtpExpiredException;
import insta_app.exception.RegistrationSessionExpired;
import insta_app.exception.UserAlreadyExistByEmail;
import insta_app.exception.UserAlreadyLogin;
import insta_app.exception.UserNotExistByEmail;
import insta_app.exception.UserPasswordNotExist;
import insta_app.exception.UserRoleNotValid;
import insta_app.mail.MailService;
import insta_app.repo.AuthRepo;
import insta_app.requestdto.OtpRequest;
import insta_app.requestdto.UseReq;
import insta_app.requestdto.UserRequest;
import insta_app.responsedto.UseRes;
import insta_app.responsedto.UserRes;
import insta_app.service.AuthService;
import insta_app.utility.MessageModel;
import insta_app.utility.ResponseStructure;
import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService{

	private AuthRepo authrepo;
	private ResponseStructure<UserRes> structure;
	private ResponseStructure<UseRes> structureRes;
	private CacheStore<String> otpCache;

	private CacheStore<User> userCache;
	
	private MailService mailService;


	



	public AuthServiceImpl(AuthRepo authrepo, ResponseStructure<UserRes> structure,
			ResponseStructure<UseRes> structureRes, CacheStore<String> otpCache, CacheStore<User> userCache,
			MailService mailService) {
		this.authrepo = authrepo;
		this.structure = structure;
		this.structureRes = structureRes;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.mailService = mailService;
	}


	@Override
	public ResponseEntity<ResponseStructure<UserRes>> save(UserRequest user)  {

//		Optional<User> check=authrepo.findByEmail(user.getEmail());
//
//		if(check.isEmpty())
//		{
//			User save = authrepo.save(changeUserRequesttoUser(user, new User()));
//
//			return ResponseEntity.ok(structure.setMessage("Data Saved Successfully").setStatuscode(HttpStatus.OK.value()).setBody(changeUsertoUserReq(save, new UserRes())));
//		}
//
//		throw new UserAlreadyExistByEmail("Email Already Exist");

		if(authrepo.existsByEmail(user.getEmail()))
			throw new  UserAlreadyExistByEmail("Email Already Exist");
		
		User user2 = changeUserRequesttoUser(user, new User());
		
		String otp=generateOtp();
		
		otpCache.add(user.getEmail(), otp);
		userCache.add(user.getEmail(), user2);
		
		System.out.println(otp);
		
		try {
			sendOtp(user2, otp);
		} catch (MessagingException e) {
			
			throw new InvalidEmailSpefied("Invalid Email Specified");
		}
		
		return ResponseEntity.ok(structure.setMessage("Otp Send to email Please Enter").setStatuscode(HttpStatus.ACCEPTED.value()));
		
	}
	
	
	private void sendOtp(User user, String otp) throws MessagingException
	{
		MessageModel message=new MessageModel(user.getEmail(), "Verify Your Otp",
				"<p> Hi, Thanks for your interest in E-comm ,Please Verify  your mail id using  the OTP given below </p> "
						+  "<h1>"+otp +"</h1>"+ "<br>"+"<p> please ignore if its not you  </P>"+"<br>"+"With best Regards"+"<h3> E-Comm </h3>");

		mailService.sendMailMessage(message);
	}

	

	private String generateOtp() {
		// TODO Auto-generated method stub
		
		SecureRandom rand=new SecureRandom();
		int otp=100000 + rand.nextInt(900000);
		
		return String.valueOf(otp);
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

	@Override
	public ResponseEntity<ResponseStructure<UserRes>> verify_otp(OtpRequest otpReq) {
		
		
		if(otpCache.get(otpReq.getEmail())==null)
			throw new OtpExpiredException("OtpExpired");
		if(!otpCache.get(otpReq.getEmail()).equals(otpReq.getOtp()))
			throw new UserAlreadyExistByEmail("Invalid Otp");

		User user = userCache.get(otpReq.getEmail());

		if(user==null)
			throw new RegistrationSessionExpired("Invalid OTP");

		user.setVerify(true);
		
		User save = authrepo.save(user);
		
		return ResponseEntity.ok(structure.setMessage("Details Saved Successfullt").setStatuscode(HttpStatus.OK.value()).setBody(changeUsertoUserReq(save, new UserRes())));
	}

	
}

