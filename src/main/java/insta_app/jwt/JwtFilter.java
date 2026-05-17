package insta_app.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import insta_app.exception.TokenExpiredException;
import insta_app.repo.AccessTokenRepo;
import insta_app.repo.RefreshTokenRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
	
	private AccessTokenRepo accessRepo;
	
	private RefreshTokenRepo refreshRepo;
	
	private JwtService jwtService;
	
	

	public JwtFilter(AccessTokenRepo accessRepo, RefreshTokenRepo refreshRepo, JwtService jwtService) {
		this.accessRepo = accessRepo;
		this.refreshRepo = refreshRepo;
		this.jwtService = jwtService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("In Jwt Filter");	
		Cookie[] cookies = request.getCookies();
		String at=null;
		String rt=null;
		
		if(cookies != null) {
			for(Cookie c:cookies) {
				if(c.getName().equals("at"))
					at=c.getValue();
				if(c.getName().equals("rt"))
					rt=c.getValue();
			}	
		}
		if(at!=null && rt!=null)
		{
			System.out.println("at and rt are not null");
			
			if(accessRepo.existsByTokenAndIsBlocked(at,true) && refreshRepo.existsByTokenAndIsBlocked(rt,true))
				throw new TokenExpiredException("Access Denied");
			String username=jwtService.getUsername(at);
			String role=jwtService.getRole(at);
			System.out.println("role"+role);
			if(username!=null && role!=null && SecurityContextHolder.getContext().getAuthentication() == null)
			{		
				UsernamePasswordAuthenticationToken token = new	UsernamePasswordAuthenticationToken(username,null,Collections.singleton(new SimpleGrantedAuthority(role)));	
				token.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
				System.out.println("Authentication Successfull");
			}
		}
		filterChain.doFilter(request, response);
	}

		
	}

	

