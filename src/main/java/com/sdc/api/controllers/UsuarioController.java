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

import com.sdc.api.dtos.SenhaDto;
import com.sdc.api.dtos.UsuarioCadastroDto;
import com.sdc.api.dtos.UsuarioDto;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;
import com.sdc.api.entities.Usuario;
import com.sdc.api.services.ClienteService;
import com.sdc.api.services.EnderecoService;
import com.sdc.api.services.UsuarioService;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EnderecoService enderecoService;

	/**
	 * Retorna os dados de um usuario a partir do id informado
	 *
	 * @param Id do usuário
	 * @return Dados do usuário
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public /*ResponseEntity<Response<UsuarioDto>>*/ void buscarPorId(@PathVariable("id") int id) {
		/*Response<UsuarioDto> response = new Response<UsuarioDto>();

		try {
			log.info("Controller: buscando usuario com id: {}", id);
			Optional<Usuario> usuario = usuarioService.buscarPorId(id);
			response.setDados(ConversaoUtils.Converter(usuario.get()));
			return ResponseEntity.ok(response);
		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}*/
	}
	
	/**
	 * Realiza o cadastro de um usuário.
	 *
	 * @param Dados de entrada do usuário
	 * @return Dados do usuario cadastrado
	 */
	@PostMapping(value = "/registrar")
	public ResponseEntity<Response<UsuarioCadastroDto>> salvarCadastro(@Valid @RequestBody UsuarioCadastroDto usuarioCadastroDto,BindingResult result) {
		
		Response<UsuarioCadastroDto> response = new Response<UsuarioCadastroDto>();
		
		try {
			log.info("Controller: salvando o usuario: {}", usuarioCadastroDto.toString());
			
			// Verificando se todos os campos da DTO foram preenchidos
			if (result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			// Converte o objeto usuarioCadastroDto para um objeto do tipo Usuario
			Usuario usuario = ConversaoUtils.Converter(usuarioCadastroDto);
			
			// Converte o objeto usuarioCadastroDto para um objeto do tipo Cliente
			Cliente cliente = ConversaoUtils.ConverterUsuarioCliente(usuarioCadastroDto);
			
			//Converte o objeto usuarioCadastroDto para um objeto do tipo Endereco
			Endereco endereco = ConversaoUtils.ConverterEndereco(usuarioCadastroDto);
			
			//Salvando o Usuário
			Usuario usuarioPersistido = this.usuarioService.salvar(usuario);
			
			// Salvando o cliente
			if(usuarioPersistido.getId() > 0 ) {
				cliente.getUsuario().setId(usuarioPersistido.getId());
				Cliente clientePersistido = this.clienteService.salvar(cliente);
				
				if(clientePersistido.getId() > 0 || clientePersistido.getRg() != null) {
					this.enderecoService.salvar(endereco);
				}
			}
			
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

	/**
	 * Persiste um usuário na base.
	 *
	 * @param Dados de entrada do usuário
	 * @return Dados do usuario persistido
	 */
	@PostMapping(value = "/salvar")
	public void /*ResponseEntity<Response<UsuarioDto>>*/ salvar(@Valid @RequestBody UsuarioDto usuarioDto,BindingResult result) {
		/*Response<UsuarioDto> response = new Response<UsuarioDto>();
		try {
			log.info("Controller: salvando o usuario: {}", usuarioDto.toString());
			
			// Verificando se todos os campos da DTO foram preenchidos
			if (result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			// Converte o objeto usuarioDto para um objeto do tipo Usuario (entidade)
			Usuario usuario = ConversaoUtils.Converter(usuarioDto);
			
			// Salvando o usuário
			response.setDados(ConversaoUtils.Converter(this.usuarioService.salvar(usuario)));
			return ResponseEntity.ok(response);
		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}*/
	}

	/**
	 * Altera a senha do usuário, verificando o próprio usuário e a senha atual.
	 *
	 * @param Dados de entrada do usuário
	 * @return Dados do usuario persistido
	 */
	@PostMapping(value = "/senha")
	public /*ResponseEntity<Response<SenhaDto>>*/ void alterarSenhaUsuario(@Valid @RequestBody SenhaDto senhaDto, BindingResult result) {
		/*Response<SenhaDto> response = new Response<SenhaDto>();
		try {
			log.info("Controller: alterando a senha do usuário: {}", senhaDto.getIdUsuario());
			// Verificando se todos os campos da DTO foram preenchidos
			if (result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {

					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			// Alterando a senha do usuário

			this.usuarioService.alterarSenhaUsuario(senhaDto.getSenhaAtual(), senhaDto.getNovaSenha(),
					Integer.parseInt(senhaDto.getIdUsuario()));
			response.setDados(senhaDto);
			return ResponseEntity.ok(response);
		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}*/
	}
}
