package insta_app.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import insta_app.exception.InvalidEmailSpefied;
import insta_app.exception.OtpExpiredException;
import insta_app.exception.RegistrationSessionExpired;
import insta_app.exception.TokenExpiredException;
import insta_app.exception.UserAlreadyExistByEmail;
import insta_app.exception.UserAlreadyLogin;
import insta_app.exception.UserNotExistByEmail;
import insta_app.exception.UserPasswordNotExist;
import insta_app.exception.UserRoleNotValid;

@RestControllerAdvice
public class HandleException {

	private ErrorStructure errorStructure;

	public HandleException(ErrorStructure errorStructure) {
		this.errorStructure = errorStructure;
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleUserAlreadyExistByEmail(UserAlreadyExistByEmail ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleUserNotExistByEmail(UserNotExistByEmail ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleUserPasswordNotExist(UserPasswordNotExist ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleUserRoleNotValid(UserRoleNotValid ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleUserAlreadyLogin(UserAlreadyLogin ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleInvalidEmailSpecified(InvalidEmailSpefied ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleOtpExpiredException(OtpExpiredException ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
	

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleRegistrationExpiredException (RegistrationSessionExpired ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> HandleTokenExpiredException (TokenExpiredException ex)
	{

		return ResponseEntity.badRequest().body(errorStructure.setMessage(ex.getMessage()).setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}
}

