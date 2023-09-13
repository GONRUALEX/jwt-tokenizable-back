package com.crud.basico.crud.security.jwt;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.crud.basico.crud.security.dto.JwtDto;
import com.crud.basico.crud.security.entity.UsuarioPrincipal;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		List<String> roles= usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		/*
		 * sub (Subject): Esta reclamación representa el sujeto del token, es decir, la identidad del usuario o entidad a la que se refiere el token. Normalmente, contiene un identificador único del usuario, como un nombre de usuario o un ID de usuario.
		iss (Issuer): Indica quién emitió el token. Esto suele ser una URL o un identificador único de la entidad que generó el token.
		exp (Expiration Time): Define la fecha y hora en la que el token expira y ya no es válido. Después de esta fecha, el token no debe ser aceptado por la aplicación y debe ser renovado o solicitarse un nuevo token.
		nbf (Not Before): Especifica la fecha y hora a partir de la cual el token es válido y puede ser utilizado. Antes de esta fecha, el token no debe ser aceptado por la aplicación.
		iat (Issued At): Indica la fecha y hora en la que se emitió el token. Esto puede ser útil para determinar cuándo se creó el token.
		aud (Audience): Define la audiencia a la que está destinado el token. Puede ser un servidor específico o una lista de servidores que deben procesar el token. Esto ayuda a garantizar que el token sea válido solo para un propósito específico.
		jti (JWT ID): Proporciona un identificador único para el token JWT. Esto puede ser útil para evitar la reutilización de tokens.*/
		return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+expiration))
				.signWith(this.getSecret(secret))
				.compact();
	}
	
	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException e) {
			logger.error("token mal formado");
		}catch (UnsupportedJwtException e) {
			logger.error("token no soportado");
		}catch (ExpiredJwtException e) {
			logger.error("token expirado");
		}catch (IllegalArgumentException e) {
			logger.error("token vacío");
		}catch (SignatureException e) {
			logger.error("fail en la firma");
		}
		
		return false;
	}
	
	private Key getSecret(String secret) {
		byte[] secretBytes = Decoders.BASE64URL.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
	
	public String refreshToken(JwtDto jwtDto) throws ParseException {
		try {
			Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(jwtDto.getToken());
		}catch(ExpiredJwtException e){
			JWT jwt = JWTParser.parse(jwtDto.getToken());
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			String nombreUsuario = claims.getSubject();
			List<String> roles =(List<String>) claims.getClaim("roles");
			
			return Jwts.builder().setSubject(nombreUsuario)
					.claim("roles", roles)
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+expiration))
					.signWith(this.getSecret(secret))
					.compact();
		}
		return null;
	}

}
