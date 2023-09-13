package com.crud.basico.crud.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crud.basico.crud.security.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			if (token!=null && jwtProvider.validateToken(token)) {//comprobamos token valido y que existe
				String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);// obtemos el usuario a partir del token
				UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario); //creamos un userdetails
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities() );//autenticamos el userdetails que será el usuario que va a estar autenticado
				SecurityContextHolder.getContext().setAuthentication(auth);//le pasamos el usuario al contexto de autenticación
			}
			
		}catch(Exception e) {
			logger.error("failen el método filter");
		}
		filterChain.doFilter(request, response);//si todo va bien llamamos a doFilter para proseguir
	}
	
	
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if (null!=header && header.startsWith("Bearer ")) {
			return header.replace("Bearer ", "");
		}
		return null;
	}

}
