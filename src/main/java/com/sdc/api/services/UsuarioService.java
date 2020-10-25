package com.sdc.api.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository testezao;

	public /*Optional<Usuario>*/ void buscarPorId(int id) {

		log.info("Service: buscando um usuário com o id: {}", id);
		
	}

	public /*Optional<Usuario>*/ void buscarPorCpf(String cpf) {

		log.info("Service: buscando um usuário com o cpf: {}", cpf);

	}

	public /*Usuario*/ void salvar(Usuario usuario) {

		log.info("Service: salvando o usuário: {}", usuario);
	
	}
}
