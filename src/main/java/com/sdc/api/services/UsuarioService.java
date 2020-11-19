package com.sdc.api.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.sdc.api.entities.Regra;
import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.RegraRepository;
import com.sdc.api.repositories.UsuarioRepository;
import com.sdc.api.security.utils.JwtTokenUtil;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.SenhaUtils;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RegraRepository regraReprository;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public Optional<Usuario> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando um usuário com o id: {}", id);

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", id);
		}

		return usuario;

	}

	public Optional<Usuario> buscarPorCpf(String cpf) throws ConsistenciaException {
		log.info("Service: buscando um usuário com o cpf: {}", cpf);

		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByCpf(cpf));

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com cpf: {} foi encontrado", cpf);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", cpf);
		}

		return usuario;

	}

	public Optional<Usuario> verificarCredenciais(String cpf) throws ConsistenciaException {
		log.info("Service: criando credenciais para o usuário de cpf: '{}'", cpf);

		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByCpf(cpf));

		log.info("Service: passou");
		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário ativo com cpf: {} foi encontrado", cpf);
			throw new ConsistenciaException("Nenhum usuário ativo com cpf: {} foi encontrado", cpf);
		}
		usuario.get().setRegras(
				usuario.get().getRegras().stream().filter(r -> r.getAtivo() == true).collect(Collectors.toList()));

		return usuario;
	}

	public Usuario salvar(Usuario usuario) throws ConsistenciaException {
		log.info("Service: salvando o usuario: {}", usuario);

		if (usuario.getId() > 0) {

			Optional<Usuario> usr = buscarPorId(usuario.getId());

			usuario.setSenha(usr.get().getSenha());

		} else {
			usuario.setSenha(SenhaUtils.gerarHash(usuario.getSenha()));
		}

		List<Regra> aux = new ArrayList<Regra>();
		String nomeRegra = "";

		if (usuario.getTipo() == 1)
			nomeRegra = "ROLE_FUNCIONARIO";
		else
			nomeRegra = "ROLE_CLIENTE";

		Optional<Regra> rg = Optional.ofNullable(regraReprository.findByNome(nomeRegra));
		aux.add(rg.get());
		usuario.setRegras(aux);

		try {

			return usuarioRepository.save(usuario);

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O cpf '{}' já está cadastrado para outro usuário", usuario.getCpf());

			throw new ConsistenciaException("O cpf '{}' já está cadastrado para outro usuário", usuario.getCpf());
		}
	}

	public void alterarSenhaUsuario(String senhaAtual, String novaSenha, int id) throws ConsistenciaException {
		log.info("Service: alterando a senha do usuário: {}", id);

		Optional<Usuario> usr = buscarPorId(id);

		String token = httpServletRequest.getHeader("Authorization");
		
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		
		String username = jwtTokenUtil.getUsernameFromToken(token);

		// Verificar se o usuário do token é diferente do usuário que está sendo
		// alterado
		if (!usr.get().getCpf().equals(username)) {
			log.info("Service: Cpf do token diferente do cpf do usuário a ser alterado");
			throw new ConsistenciaException("Você não tem permissão para alterar a senha de outro usuário.");
		}

		// Verificar se a senha atual do usuário diferente da informada na entrada
		if (!SenhaUtils.compararHash(senhaAtual, usr.get().getSenha())) {
			log.info("Service: A senha atual informada não é válida");
			throw new ConsistenciaException("A senha atual informada não é válida.");
		}

		usuarioRepository.alterarSenhaUsuario(SenhaUtils.gerarHash(novaSenha), id);
	}

}
