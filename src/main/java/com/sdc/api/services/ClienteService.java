package com.sdc.api.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Cliente;
import com.sdc.api.repositories.ClienteRepository;

@Service
public class ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
	
	@Autowired
	private ClienteRepository clienteRepository;

	public /*Optional<Cliente>*/ void buscarPorId(int id) {

		log.info("Service: buscando um cliente com o id: {}", id);
		
	}

	public /*Optional<Cliente>*/ void buscarPorRg(String rg) {

		log.info("Service: buscando um cliente com o rg: {}", rg);

	}

	public /*Optional<Cliente>*/ void buscarPorEmail(String email) {

		log.info("Service: buscando um cliente com o email: {}", email);

	}

	public /*Usuario*/ void salvar(Cliente cliente) {

		log.info("Service: salvando o cliente: {}", cliente);
	
	}
}
