package com.sdc.api.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Combustivel;
import com.sdc.api.repositories.CombustivelRepository;
import com.sdc.api.utils.ConsistenciaException;

@Service
public class CombustivelService {

	private static final Logger log = LoggerFactory.getLogger(CombustivelService.class);
	
	@Autowired
	private CombustivelRepository combustivelRepository;

	public Optional<Combustivel> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando combustivel de id: {}", id);

		Optional<Combustivel> combustivel = combustivelRepository.findById(id);

		if (!combustivel.isPresent()) {
			log.info("Service: Nenhum combustivel de id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum combustivel de id: {} foi encontrado", id);
		}

		return combustivel;
		
	}
	
	public Optional<Combustivel> buscarPorTipo(int tipo) throws ConsistenciaException {

		log.info("Service: buscando combustivel de tipo: {}", tipo);

		Optional<Combustivel> combustivel = combustivelRepository.findByTipo(tipo);

		if (!combustivel.isPresent()) {
			log.info("Service: Nenhum combustivel de tipo: {} foi encontrado", tipo);
			throw new ConsistenciaException("Nenhum combustivel de tipo: {} foi encontrado", tipo);
		}

		return combustivel;
		
	}
	
	public Optional<List<Combustivel>> listar() throws ConsistenciaException {
		log.info("Service: buscando todas os combustiveis");

		Optional<List<Combustivel>> combustiveis = Optional.ofNullable(combustivelRepository.findAll());
		
		if (!combustiveis.isPresent() || combustiveis.get().size() < 1) {
			log.info("Service: Nenhum combustivel foi encontrado");
			throw new ConsistenciaException("Nenhum combustivel foi encontrado");
		}
		
		return combustiveis;
	}

	public Combustivel salvar(Combustivel combustivel) throws ConsistenciaException {

		if (combustivel.getId() > 0)
			buscarPorId(combustivel.getId());
		
		try {
			combustivel.setDataAtualizacao(new Date());
			return combustivelRepository.save(combustivel);

		} catch (DataIntegrityViolationException e) {

			log.info("Service: Já existe um combustivel de tipo {} cadastrado", combustivel.getTipo());
			throw new ConsistenciaException("Já existe um combustivel de tipo {} cadastrado", combustivel.getTipo());
		}
	}
}
