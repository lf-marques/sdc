package com.sdc.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.UsuarioRepository;
import com.sdc.api.security.utils.JwtTokenUtil;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.SenhaUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;


	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	private Usuario usuarioTeste;

	private void CriarUsuarioTestes() throws ParseException {

		usuarioTeste = new Usuario();

		usuarioTeste.setId(1);
		usuarioTeste.setNome("Fulano de Tal");
		usuarioTeste.setCpf("12345678901");
		usuarioTeste.setSenha("senha123");
		usuarioTeste.setTipo(1);

	}

	@Before
	public void setUp() throws Exception {

		CriarUsuarioTestes();
		usuarioRepository.save(usuarioTeste);

	}

	@After
	public void tearDown() throws Exception {

		usuarioRepository.deleteAll();

	}

	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException {
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Usuario()));

		Optional<Usuario> resultado = usuarioService.buscarPorId(1);

		assertTrue(resultado.isPresent());
	}

	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {

		usuarioService.buscarPorId(-2);

	}


	@Test
	public void testSalvarComSucesso() throws ConsistenciaException {
		
		Usuario usuario = new Usuario();
				
		BDDMockito.given(usuarioRepository.save(Mockito.any(Usuario.class)))
        .willReturn(new Usuario());
		
		Usuario resultado = usuarioService.salvar(usuario);

		assertNotNull(resultado);
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testSalvarSemSucesso() throws ConsistenciaException {
		
		usuarioService.salvar(usuarioTeste);
	}

	@Test
	@WithMockUser(password = "senhaAtual", username = "12345678998")
	public void testAlterarSenhaUsuario() throws ConsistenciaException {
		
		Usuario usuario = new Usuario();
		usuario.setCpf("12345678998");
		usuario.setId(1);
		usuario.setSenha(SenhaUtils.gerarHash("senhaAtual"));
		
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.of(usuario));
		
		BDDMockito.given(jwtTokenUtil.getUsernameFromToken(Mockito.any())).willReturn(usuario.getCpf());

		usuarioService.alterarSenhaUsuario("senhaAtual", "novaSenha", 1);
	}
}