package com.crud.basico.crud.security.email.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.basico.crud.dto.Mensaje;
import com.crud.basico.crud.security.email.dto.ChangePasswordDto;
import com.crud.basico.crud.security.email.dto.EmailValuesDto;
import com.crud.basico.crud.security.email.service.EmailService;
import com.crud.basico.crud.security.entity.Usuario;
import com.crud.basico.crud.security.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

	@Autowired
	EmailService emailService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Value("${spring.mail.mailFrom}")
	private String mailFrom;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private static final String subject="cambio de contraseña";
	
	@PostMapping("/send-email")
	public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDto dto){
		Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuarioOrEmail(dto.getMailTo());
		if(!usuarioOpt.isPresent()) {
			return new ResponseEntity(new Mensaje("No existe ningún usuario con ese nombre"), HttpStatus.NOT_FOUND);
		}
		
		Usuario usuario = usuarioOpt.get();
		dto.setMailFrom(mailFrom);
		dto.setSubject(subject);
		dto.setUserName(usuario.getNombreUsuario());
		dto.setMailTo(usuario.getEmail());
		
		UUID uuid = UUID.randomUUID();
		String tokenPassword = uuid.toString();
		dto.setTokenPassword(tokenPassword);
		usuario.setTokenPassword(tokenPassword);
		usuarioService.save(usuario);
		emailService.sendEmailTemplate(dto); 
		return new ResponseEntity(new Mensaje("Enviado correo"), HttpStatus.OK);
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto dto, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		}
		if(!dto.getPassword().equals(dto.getConfirmPassword())) {
			return new ResponseEntity(new Mensaje("Contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
		}
		Optional<Usuario> usuarioOpt = usuarioService.getByTokenPassword(dto.getTokenPassword());
		if(!usuarioOpt.isPresent()) {
			return new ResponseEntity(new Mensaje("No existe ningún usuario con estas crendenciales"), HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = usuarioOpt.get();
		String password = passwordEncoder.encode(dto.getPassword());
		usuario.setPassword(password);
		usuario.setTokenPassword(null);
		usuarioService.save(usuario);
		return new ResponseEntity(new Mensaje("Contraseña actualizada con éxito!"), HttpStatus.OK);
	}	
	
}
