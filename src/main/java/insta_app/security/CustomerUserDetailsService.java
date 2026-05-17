package insta_app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import insta_app.repo.AuthRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

 private AuthRepo authRepo;

public CustomerUserDetailsService(AuthRepo authRepo) {
	this.authRepo = authRepo;
}


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	// TODO Auto-generated method stub
	return authRepo.findByEmail(username).map(CustomerUserDetails::new).orElseThrow(()->new UsernameNotFoundException(username));
}
 	
	
 
	
}
