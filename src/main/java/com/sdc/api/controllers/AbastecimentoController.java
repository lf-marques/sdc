package com.sdc.api.controllers;

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

import com.sdc.api.dtos.AbastecimentoDto;
import com.sdc.api.dtos.CombustivelDto;
import com.sdc.api.dtos.UsuarioDto;
import com.sdc.api.entities.Abastecimento;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.services.AbastecimentoService;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/abastecimento")
@CrossOrigin(origins = "*")
public class AbastecimentoController {

	private static final Logger log = LoggerFactory.getLogger(AbastecimentoController.class);

	@Autowired
	private AbastecimentoService abastecimentoService;

	@PostMapping(value = "/buscarPorId")
	public ResponseEntity<Response<AbastecimentoDto>> buscarPorId(@RequestBody AbastecimentoDto abastecimentoDto,
			BindingResult result) {
		Response<AbastecimentoDto> response = new Response<AbastecimentoDto>();

		try {
			log.info("Controller: buscando abastecimento de id: {}", abastecimentoDto.getId());
			Optional<Abastecimento> abastecimento = abastecimentoService.buscarPorId(Integer.parseInt(abastecimentoDto.getId()));
			response.setDados(ConversaoUtils.Converter(abastecimento.get()));
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

	@PostMapping(value = "/salvar")
	public ResponseEntity<Response<AbastecimentoDto>> salvar(@Valid @RequestBody AbastecimentoDto abastecimentoDto,
			BindingResult result) {
		Response<AbastecimentoDto> response = new Response<AbastecimentoDto>();

		try {
			
			log.info("Controller: salvando o abastecimento: {}", abastecimentoDto.toString());

			if (result.hasErrors()) {

				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			Abastecimento abastecimento = ConversaoUtils.Converter(abastecimentoDto);
			response.setDados(ConversaoUtils.Converter(this.abastecimentoService.salvar(abastecimento)));

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
