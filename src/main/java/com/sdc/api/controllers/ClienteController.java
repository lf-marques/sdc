package com.sdc.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;
import com.sdc.api.dtos.ClienteDto;
import com.sdc.api.entities.Cliente;
import com.sdc.api.services.ClienteService;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> buscarPorId(@PathVariable("id") int id) {

		Response<ClienteDto> response = new Response<ClienteDto>();

		try {
			log.info("Controller: buscando cliente com id: {}", id);
			Optional<Cliente> cliente = clienteService.buscarPorId(id);
			response.setDados(ConversaoUtils.Converter(cliente.get()));
			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

   	@PreAuthorize("hasAnyRole('CLIENTE')")
	@PostMapping
	public ResponseEntity<Response<ClienteDto>> salvar(@Valid @RequestBody ClienteDto clienteDto,
			BindingResult result) {

		Response<ClienteDto> response = new Response<ClienteDto>();

		try {

			log.info("Controller: salvando o cliente: {}", clienteDto.toString());

			if (result.hasErrors()) {

				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			Cliente cliente = this.clienteService.salvar(ConversaoUtils.Converter(clienteDto));
			response.setDados(ConversaoUtils.Converter(cliente));

			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {

			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
}
