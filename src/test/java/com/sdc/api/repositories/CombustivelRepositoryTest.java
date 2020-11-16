package com.sdc.api.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Combustivel;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CombustivelRepositoryTest {
	@Autowired
	CombustivelRepository combustivelRepository;

	Combustivel combustivelTeste;

	private void CriarCombustivelTestes() throws ParseException {

		combustivelTeste = new Combustivel();

		combustivelTeste.setId(1);
		combustivelTeste.setTipo(1);
		combustivelTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		combustivelTeste.setTitulo("Gososa");
		combustivelTeste.setValor(200.00);
	}

	@Before
	public void setUp() throws Exception {

		CriarCombustivelTestes();
		combustivelRepository.save(combustivelTeste);

	}

	@After
	public void tearDown() throws Exception {

		combustivelRepository.deleteAll();
	}

	@Test
	public void findByTipo() {
		Optional<Combustivel> combustivel = combustivelRepository.findByTipo(combustivelTeste.getTipo());
		assertEquals(combustivel.get().getTipo(), combustivelTeste.getTipo());
	}

}