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

import com.sdc.api.entities.Regra;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RegraRepositoryTest {
	@Autowired
	RegraRepository regraRepository;

	Regra regraTeste;

	private void CriarClienteTestes() throws ParseException {

		regraTeste = new Regra();

		regraTeste.setNome("Patrao");
		regraTeste.setDescricao("Esse cara é o pika do sistema");
		regraTeste.setAtivo(true);
	}

	@Before
	public void setUp() throws Exception {

		CriarClienteTestes();
		regraRepository.save(regraTeste);

	}

	@After
	public void tearDown() throws Exception {

		regraRepository.deleteAll();
	}

	@Test
	public void findByNome() {
		Regra regra = regraRepository.findByNome(regraTeste.getNome());
		assertEquals(regra.getNome(), regra.getNome());
	}

}