package insta_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import insta_app.jwt.JwtFilter;
import insta_app.jwt.JwtService;
import insta_app.jwt.RefreshFilter;
import insta_app.repo.AccessTokenRepo;
import insta_app.repo.RefreshTokenRepo;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private CustomerUserDetailsService customDetails;

	private AccessTokenRepo accessTokenRepo;

	private JwtService jwtService;

	private RefreshTokenRepo refreshTokenRepo;

	public SecurityConfig(CustomerUserDetailsService customDetails, AccessTokenRepo accessTokenRepo,
			JwtService jwtService, RefreshTokenRepo refreshTokenRepo) {
		this.customDetails = customDetails;
		this.accessTokenRepo = accessTokenRepo;
		this.jwtService = jwtService;
		this.refreshTokenRepo = refreshTokenRepo;
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider auth= new DaoAuthenticationProvider();
		auth.setPasswordEncoder(passwordEncoder());
		auth.setUserDetailsService(customDetails);
		return auth;
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/login", "/save", "/verify_email").permitAll()
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
	            .authenticationProvider(authenticationProvider())
	            .build();
	}


	@Bean
	JwtFilter jwtFilter() {
	    return new JwtFilter(accessTokenRepo, refreshTokenRepo, jwtService);
	}

	@Bean
	RefreshFilter refreshFilter() {
	    return new RefreshFilter(jwtService, refreshTokenRepo);
	}
	
	
//	@Bean
//	@Order(1)
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf(csrf -> csrf.disable())
////				.securityMatchers(matchers->matchers.requestMatchers("/api/v1/users/login","/api/v1/users/user","/api/v1/verify_email"))
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/login","/save","/verify_email").permitAll().anyRequest().authenticated())
//				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(new JwtFilter(accessTokenRepo, refreshTokenRepo, jwtService), UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(authenticationProvider())
//				.build();
//	}
//
//	@Bean
//	@Order(2)
//	SecurityFilterChain refreshFilterChain(HttpSecurity http) throws Exception{
//		return http.csrf(csrf->csrf.disable())
//				.securityMatchers(matcher->matcher.requestMatchers("/home/**"))
//				.authorizeHttpRequests(auth->auth.anyRequest().authenticated())
//				.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(new RefreshFilter(jwtService, refreshTokenRepo), UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(authenticationProvider()).
//				build();
//	}
//
//	@Bean
//	@Order(3)
//	SecurityFilterChain anyRequest(HttpSecurity http) throws Exception
//	{ 
//		return http.csrf(csrf->csrf.disable())
//				.securityMatchers(mathcers->mathcers.requestMatchers("/**"))
//				.authorizeHttpRequests(auth->auth.anyRequest().authenticated())
//				.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(new JwtFilter(accessTokenRepo, refreshTokenRepo, jwtService), UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(authenticationProvider())
//				.build();
//	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
		return ac.getAuthenticationManager();
	}

}
