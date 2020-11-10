package com.sdc.api.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Abastecimento;
import com.sdc.api.repositories.AbastecimentoRepository;

@Service
public class AbastecimentoService {

	private static final Logger log = LoggerFactory.getLogger(AbastecimentoService.class);
	
	@Autowired
	private AbastecimentoRepository abastecimentoRepository;

	public /*Optional<Combustivel>*/ void buscarPorClienteId(int clienteId) {

		log.info("Service: buscando um abastecimento do cliente: {}", clienteId);
		
	}

	public /*Abastecimento*/ void salvar(Abastecimento abastecimento) {

		log.info("Service: salvando o abastecimento: {}", abastecimento);
	
	}
}
