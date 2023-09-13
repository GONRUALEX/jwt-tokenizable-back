package com.crud.basico.crud.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.crud.basico.crud.dto.Mensaje;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint{
//comprueba si hay o no token, si no lo hay manda un 404 
	private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = "No autorizado";

        /**if (authException instanceof BadCredentialsException) {
            errorMessage = "Credenciales incorrectas";
        } else if (authException instanceof LockedException) {
            errorMessage = "La cuenta está bloqueada";
        } else if (authException instanceof DisabledException) {
            errorMessage = "La cuenta está deshabilitada";
        } else if (authException instanceof AccountExpiredException) {
            errorMessage = "La cuenta ha expirado";
        } else if (authException instanceof UsernameNotFoundException) {
            errorMessage = "El usuario no es quién dice ser";
        } else if (authException instanceof InternalAuthenticationServiceException) {
            errorMessage = "El usuario no es quién dice ser";
        }  // Agrega más casos según tus necesidades**/

        logger.error("Error en el método commence: " + authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
		
	}
	
	
}
