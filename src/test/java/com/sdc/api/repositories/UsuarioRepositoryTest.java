package com.sdc.api.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	@Autowired
	UsuarioRepository usuarioRepository;

	Usuario usuarioTeste;
	
	private void CriarClienteTestes() throws ParseException {
		
		usuarioTeste = new Usuario();
		
		usuarioTeste.setId(1);
		usuarioTeste.setNome("Josefino");
		usuarioTeste.setCpf("12345678910");
		usuarioTeste.setSenha("senhalegal1234");
		usuarioTeste.setTipo(2);
	}
	
	@Before
	public void setUp() throws Exception {
		
		CriarClienteTestes();
		usuarioRepository.save(usuarioTeste);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		usuarioRepository.deleteAll();
		
	}
	
	@Test
	public void findByCpf() {
		Usuario usuario = usuarioRepository.findByCpf(usuarioTeste.getCpf());
		assertEquals(usuario.getCpf(), usuarioTeste.getCpf());
	}
	
	@Test
	public void alterarSenhaUsuario() {
		String novasenha = "novasenha123";
		usuarioRepository.alterarSenhaUsuario(novasenha, usuarioTeste.getId());
	}
}
