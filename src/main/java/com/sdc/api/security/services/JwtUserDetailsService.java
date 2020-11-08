package com.sdc.api.security.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sdc.api.entities.Usuario;
import com.sdc.api.security.JwtUserFactory;
import com.sdc.api.services.UsuarioService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario;

		try {
			usuario = usuarioService.verificarCredenciais(username);
			return JwtUserFactory.create(usuario.get());
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}

	}
}