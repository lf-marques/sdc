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

import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.entities.Cartao;
import com.sdc.api.repositories.CartaoRepository;
import com.sdc.api.repositories.ClienteRepository;

@Service
public class CartaoService {

	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	public /* Optional<Cartao> */ void buscarPorNumero(String numero) {

		log.info("Service: buscando um cartao com o numero: {}", numero);

	}

	public Optional<Cartao> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando os cartoes de id: {}", id);

		Optional<Cartao> cartao = cartaoRepository.findById(id);

		if (!cartao.isPresent()) {
			log.info("Service: Nenhum cartão com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhuma cartão com id: {} foi encontrado", id);
		}

		return cartao;
	}

	public Optional<List<Cartao>> buscarPorClienteId(int id) throws ConsistenciaException {

		log.info("Service: buscando os cartoes do cliente de id: {}", id);

		Optional<List<Cartao>> cartoes = Optional.ofNullable(cartaoRepository.findByClienteId(id));

		if (!cartoes.isPresent() || cartoes.get().size() < 1) {
			log.info("Service: Nenhum cartão encontrado para o cliente de id: {}", id);
			throw new ConsistenciaException("Nenhum cartão encontrado para o cliente de id: {}", id);
		}

		return cartoes;
	}
	
	public void validar(int cartaoId) throws ConsistenciaException {
		log.info("Service: Validando status do cartão de id: {} ", cartaoId);
		Cartao cartao = cartaoRepository.findById(cartaoId).get();
		String statusCartao = "";
		
		if(cartao.getId() > 0) {
			if(cartao.getBloqueado() == 1) {
				statusCartao = "O cartão se encontra bloqueado.";
			}
	     	
	     	if(cartao.getDataValidade().before(new Date())) {
	     		statusCartao = "O cartão se encontra vencido.";
	     	}
			
	     	if(statusCartao.length() > 0) {
	     		throw new ConsistenciaException("O cartão de número {} não permite finalizar o pagamento. "
	        			+ "Mensagem de erro: {}", cartao.getNumero(), statusCartao);	
	     	}
		}else {
			throw new ConsistenciaException("Validar cartao: Nenhum cartão de id {} foi encontrado.", cartao.getId());
		}
	}

	public Cartao salvar(Cartao cartao) throws ConsistenciaException {

		log.info("Service: salvando o cartao: {}", cartao);

		if (!clienteRepository.findById(cartao.getCliente().getId()).isPresent()) {
			
			log.info("Service: Nenhum cliente com id: {} foi encontrado", cartao.getCliente().getId());
			throw new ConsistenciaException("Nenhum cliente com id: {} foi encontrado", cartao.getCliente().getId());

		}

		if (cartao.getId() > 0)
			buscarPorId(cartao.getId());

		try {
			
			return cartaoRepository.save(cartao);
			
		} catch (DataIntegrityViolationException e) {
			
			log.info("Service: Já existe um cartão de número {} cadastrado", cartao.getNumero());
			throw new ConsistenciaException("Já existe um cartão de número {} cadastrado", cartao.getNumero());
		}
	}
}
