package com.sdc.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Regra;
import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.repositories.UsuarioRepository;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.SenhaUtils;

@Service
public class ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Cliente> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando um clinte com o id: {}", id);

		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (!cliente.isPresent()) {
			log.info("Service: Nenhum cliente com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum cliente com id: {} foi encontrado", id);
		}

		return cliente;
	}

	public Optional<Cliente> buscarPorRg(String rg) throws ConsistenciaException {
		
		log.info("Service: buscando um clinte com o id: {}", rg);

		Optional<Cliente> cliente = clienteRepository.findByRg(rg);

		if (!cliente.isPresent()) {
			log.info("Service: Nenhum cliente com rg: {} foi encontrado", rg);
			throw new ConsistenciaException("Nenhum cliente com rg: {} foi encontrado", rg);
		}

		return cliente;
	}
	
	public Optional<Cliente> buscarPorCpf(String cpf) throws ConsistenciaException {
		
		log.info("Service: buscando um cliente com o cpf: {}", cpf);

		Usuario usuario = usuarioRepository.findByCpf(cpf);
		if(usuario.getId() > 0) {
			Optional<Cliente> cliente = clienteRepository.findByUsuarioId(usuario.getId());	
		
			if (!cliente.isPresent()) {
				log.info("Service: Nenhum cliente com rg: {} foi encontrado", cpf);
				throw new ConsistenciaException("Nenhum cliente com rg: {} foi encontrado", cpf);
			}
			
			return cliente;
		}else {
			throw new ConsistenciaException("Nenhum usuário com cpf: {} foi encontrado", cpf);
		}
	}

	public Cliente salvar(Cliente cliente) throws ConsistenciaException {

		if (!usuarioRepository.findById(cliente.getUsuario().getId()).isPresent()) {

			log.info("Cliente Service: Nenhum usuario com id: {} foi encontrado", cliente.getUsuario().getId());

			throw new ConsistenciaException("Cliente Service: Nenhum usuario com id: {} foi encontrado",
					cliente.getUsuario().getId());
		}

		if (cliente.getId() > 0)
			buscarPorId(cliente.getId());

		try {
			return clienteRepository.save(cliente);

		} catch (DataIntegrityViolationException e) {

			log.info("Service: Já existe um cliente de rg {} cadastrado", cliente.getRg());
			throw new ConsistenciaException("Já existe um cliente de rg {} cadastrado", cliente.getRg());
		}
	}
}
