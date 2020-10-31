package com.sdc.api.security;

import com.sdc.api.entities.Usuario;

public class JwtUserFactory {

	public static JwtUser create(Usuario usuario) {

		return new JwtUser(usuario);

	}

}