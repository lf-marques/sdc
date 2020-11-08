package com.sdc.api.services;

import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Abastecimento;
import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.repositories.AbastecimentoRepository;
import com.sdc.api.repositories.CartaoRepository;
import com.sdc.api.repositories.CombustivelRepository;
import com.sdc.api.utils.ConsistenciaException;

@Service
public class AbastecimentoService {

	private static final Logger log = LoggerFactory.getLogger(AbastecimentoService.class);
	
	@Autowired
	private AbastecimentoRepository abastecimentoRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private CombustivelRepository combustivelRepository;

	@Autowired
	private CartaoService cartaoService;

	public Optional<Abastecimento> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando abastecimento de id: {}", id);

		Optional<Abastecimento> combustivel = abastecimentoRepository.findById(id);

		if (!combustivel.isPresent()) {
			log.info("Service: Nenhum abastecimento de id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum combustivel de id: {} foi encontrado", id);
		}

		return combustivel;
		
	}

	public Abastecimento salvar(Abastecimento abastecimento) throws ConsistenciaException {
    	
		if (!combustivelRepository.findById(abastecimento.getCombustivel().getId()).isPresent()) {
        	log.info("Service: Nenhum combustivel com id: {} foi encontrado", abastecimento.getCombustivel().getId());
        	throw new ConsistenciaException("Nenhum combustivel com id: {} foi encontrado", abastecimento.getCombustivel().getId());
		}

		if (cartaoRepository.findById(abastecimento.getCartao().getId()).isPresent()) {
			cartaoService.validar(abastecimento.getCartao().getId());
			abastecimento.setConfirmacaoPagamento(1);
		}else {
        	log.info("Service: Nenhum cartão com id: {} foi encontrado", abastecimento.getCartao().getId());
        	throw new ConsistenciaException("Nenhum cartão com id: {} foi encontrado", abastecimento.getCartao().getId());
		}
		
		if (abastecimento.getId() > 0)
			buscarPorId(abastecimento.getId());
		else
			abastecimento.setDataCriacao(new Date());
		
		try {
			
			return abastecimentoRepository.save(abastecimento);

		} catch (Exception e) {
			
			log.info("Service: Ocorreu um erro ao salvar o abastecimento: {}", e.getMessage());
			throw new ConsistenciaException("Ocorreu um erro ao salvar o abastecimento: {} cadastrado", e.getMessage());
		}
	}
}
