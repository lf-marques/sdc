package com.sdc.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.sdc.api.dtos.CartaoDto;
import com.sdc.api.entities.Cartao;
import com.sdc.api.services.CartaoService;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/cartao")
@CrossOrigin(origins = "*")
public class CartaoController {

	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);

	@Autowired
	private CartaoService cartaoService;

	@GetMapping(value = "/numero/{numero}")
	public /* ResponseEntity<Response<Cartao>> */ void buscarPorNumero(@PathVariable("numero") String numero) {

	}

	@GetMapping(value = "/cliente/{id}")
	public ResponseEntity<Response<List<CartaoDto>>> buscarPorClienteId(@PathVariable("id") int id) {

		Response<List<CartaoDto>> response = new Response<List<CartaoDto>>();

		try {
			log.info("Controller: buscando cartões do cliente de ID: {}", id);

			Optional<List<Cartao>> listaCartoes = cartaoService.buscarPorClienteId(id);

			response.setDados(ConversaoUtils.ConverterLista(listaCartoes.get()));

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

	@PostMapping
	public ResponseEntity<Response<CartaoDto>> salvar(@Valid @RequestBody CartaoDto cartaoDto, BindingResult result) {

		Response<CartaoDto> response = new Response<CartaoDto>();

		try {
			log.info("Controller: salvando o cartao: {}", cartaoDto.toString());

			if (result.hasErrors()) {
				
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			Cartao cartao = this.cartaoService.salvar(ConversaoUtils.Converter(cartaoDto));
			response.setDados(ConversaoUtils.Converter(cartao));

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
