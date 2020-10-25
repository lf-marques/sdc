package com.sdc.api.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Combustivel;
import com.sdc.api.repositories.CombustivelRepository;

@Service
public class CombustivelService {

	private static final Logger log = LoggerFactory.getLogger(CombustivelService.class);
	
	@Autowired
	private CombustivelRepository combustivelRepository;

	public /*Optional<Combustivel>*/ void buscarPorTipo(int tipo) {

		log.info("Service: buscando um combustivel pelo tipo: {}", tipo);
		
	}

	public /*Combustivel*/ void salvar(Combustivel combustivel) {

		log.info("Service: salvando o combustivel: {}", combustivel);
	
	}
}
