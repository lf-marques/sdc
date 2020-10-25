package com.sdc.api.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Cartao;
import com.sdc.api.repositories.CartaoRepository;

@Service
public class CartaoService {

	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);
	
	@Autowired
	private CartaoRepository testezao;

	public /*Optional<Cartao>*/ void buscarPorNumero(String numero) {

		log.info("Service: buscando um cartao com o numero: {}", numero);
		
	}

	public /*Optional<Cartao>*/ void buscarPorClienteId(int clienteId) {

		log.info("Service: buscando um cartão relacionado "
				+ "ao cliente de id: {}", clienteId);

	}

	public /*Cartao*/ void salvar(Cartao cartao) {

		log.info("Service: salvando o cartao: {}", cartao);
	
	}
}
