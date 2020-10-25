package com.sdc.api.controllers;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public /* ResponseEntity<Response<Cartao>> */ void buscarPorClienteId(@PathVariable("id") int id) {

	}

	@PostMapping
	public /* ResponseEntity<Response<Cartao>> */ void salvar(@RequestBody Cartao cartao) {

	}
}
