 package insta_app.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import insta_app.exception.TokenExpiredException;
import insta_app.repo.RefreshTokenRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RefreshFilter extends OncePerRequestFilter {
	
	
	private JwtService jwtService;
	
	private RefreshTokenRepo refreshTokenFilter;
	
	
	

	public RefreshFilter(JwtService jwtService, RefreshTokenRepo refreshTokenFilter) {
		this.jwtService = jwtService;
		this.refreshTokenFilter = refreshTokenFilter;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		
		String rt=null;
		
		if(cookies!=null)
		{
			for(Cookie c: cookies)
			{
				if(c.getName().equals("rt"));
				rt=c.getValue();
			}
		}
		
		
		if(rt!=null)
		{
			System.out.println("Refresh Filter");
			if(refreshTokenFilter.existsByTokenAndIsBlocked(rt, true))
				throw new TokenExpiredException("Access, Denied");
		}
		
		String username=jwtService.getUsername(rt);
		String role=jwtService.getRole(rt);
		System.out.println("role"+role);
		if(username!=null && role!=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{		
			UsernamePasswordAuthenticationToken token = new	UsernamePasswordAuthenticationToken(username,null,Collections.singleton(new SimpleGrantedAuthority(role)));	
			token.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(token);
			System.out.println("Authentication Successfull");
		}
	
	filterChain.doFilter(request, response);

		
	}

	

}
