package com.sdc.api.services;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.UsuarioRepository;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@MockBean
	private HttpServletRequest httpServletRequest;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	/*@Test
	public void testBuscarPorId() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarPorCpf() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerificarCredenciais() {
		fail("Not yet implemented");
	}

	@Test
	public void testSalvar() {
		fail("Not yet implemented");
	}*/

	@Test
	@WithMockUser(password = "senhaAtual", username = "12345678998")
	public void testAlterarSenhaUsuario() throws ConsistenciaException {
		
		Usuario usuario = new Usuario();
		usuario.setCpf("12345678998");
		usuario.setId(1);
		usuario.setSenha("senhaAtual");
		
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.of(usuario));
		
		BDDMockito.given(httpServletRequest.getHeader(Mockito.anyString()))
		.willReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

		usuarioService.alterarSenhaUsuario("senhaAtual", "novaSenha", 1);
	}

}