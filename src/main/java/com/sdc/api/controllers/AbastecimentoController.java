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
import com.sdc.api.entities.Abastecimento;
import com.sdc.api.services.AbastecimentoService;

@RestController
@RequestMapping("/api/abastecimento")
@CrossOrigin(origins = "*")
public class AbastecimentoController {

	private static final Logger log = LoggerFactory.getLogger(AbastecimentoController.class);

	@Autowired
	private AbastecimentoService abastecimentoService;

	@GetMapping(value = "/cliente/{id}")
	public /* ResponseEntity<(Abastecimento> */ void buscarPorClienteId(@PathVariable("id") int id) {

	}

	@PostMapping
	public /* ResponseEntity<(Abastecimento> */ void salvar(@RequestBody Abastecimento abastecimento) {

	}
}
