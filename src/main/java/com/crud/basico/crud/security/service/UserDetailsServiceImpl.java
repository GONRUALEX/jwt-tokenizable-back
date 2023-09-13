package com.crud.basico.crud.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crud.basico.crud.security.entity.Usuario;
import com.crud.basico.crud.security.entity.UsuarioPrincipal;
import com.crud.basico.crud.security.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String nombreOrEmail) {
		Usuario usuario = usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail).orElse(null);
		if (null==usuario) {
			throw new UsernameNotFoundException("Name o password incorrect");
		}
		return UsuarioPrincipal.build(usuario);
		
	}
}
