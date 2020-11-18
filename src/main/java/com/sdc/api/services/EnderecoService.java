package com.sdc.api.services;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.repositories.EnderecoRepository;
import com.sdc.api.utils.ConsistenciaException;

@Service
public class EnderecoService {

	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public Optional<List<Endereco>> buscarPorClienteId(int clienteId) throws ConsistenciaException {

		log.info("Service: buscando o endereco do cliente de id: {}", clienteId);

		Optional<List<Endereco>> enderecos = Optional.ofNullable(enderecoRepository.findByClienteId(clienteId));

		if (!enderecos.isPresent() || enderecos.get().size() < 1) {
			log.info("Service: Nenhum endereco encontrado para o cliente de id: {}", clienteId);
			throw new ConsistenciaException("Nenhum endereco encontrado para o cliente de id: {}", clienteId);
		}

		return enderecos;

	}

	public Endereco salvar(Endereco endereco) throws ConsistenciaException {

		log.info("Service: salvando o endereco: {}", endereco);
		
		if(endereco.getCliente().getId() == 0) {
			Optional<Cliente> cliente = clienteRepository.findByRg(endereco.getCliente().getRg());
			endereco.getCliente().setId(cliente.get().getId());
		}
		 
     	if (!clienteRepository.findById(endereco.getCliente().getId()).isPresent()) {

        	log.info("Service: Nenhum cliente com id: {} foi encontrado", endereco.getCliente().getId());
        	
        	throw new ConsistenciaException(
        			"Cliente Service: Nenhum cliente com id: {} foi encontrado", 
        			endereco.getCliente().getId()
			);

     	}

     	/*if (cliente.getUsuarioId() > 0)
        	buscarPorId(cliente.getUsuarioId());*/

     	try {
    		log.info("Endereco: BEFORE SAVE");
        	
    		return enderecoRepository.save(endereco);

     	} catch (DataIntegrityViolationException e) {
     		
        	log.info("Service: Já existe um endereco cadastrado para o cliente de id {}", 
        			endereco.getCliente().getId());
        	throw new ConsistenciaException(
        			"Já existe um endereco cadastrado para o cliente de id {}", 
        			endereco.getCliente().getId()
			);
            	
     	}
	}
}
