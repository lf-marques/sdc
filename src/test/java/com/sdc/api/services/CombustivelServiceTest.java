package com.sdc.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Combustivel;
import com.sdc.api.repositories.CombustivelRepository;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CombustivelServiceTest {
	@MockBean
	private CombustivelRepository combustivelRepository;
	@Autowired
	private CacheManager cache;
	@Autowired
	private CombustivelService combustivelService;

	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException {
		BDDMockito.given(combustivelRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Combustivel()));

		Optional<Combustivel> resultado = combustivelService.buscarPorId(1);

		assertTrue(resultado.isPresent());
	}

	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {

		BDDMockito.given(combustivelRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

		combustivelService.buscarPorId(1);
	}

	@Test
	public void testBuscarPorTipoExistente() throws ConsistenciaException {
		BDDMockito.given(combustivelRepository.findByTipo(Mockito.anyInt())).willReturn(Optional.of(new Combustivel()));

		Optional<Combustivel> resultado = combustivelService.buscarPorTipo(1);

		assertTrue(resultado.isPresent());
	}

	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorTipoNaoExistente() throws ConsistenciaException {

		BDDMockito.given(combustivelRepository.findByTipo(Mockito.anyInt())).willReturn(Optional.empty());

		combustivelService.buscarPorTipo(1);
	}

	@Test
	public void testListarCombustiveis() throws ConsistenciaException {

		List<Combustivel> lstcombustivel = new ArrayList<Combustivel>();
		lstcombustivel.add(new Combustivel());

		BDDMockito.given(combustivelRepository.findAll()).willReturn((lstcombustivel));

		Optional<List<Combustivel>> resultado = combustivelService.listar();

		assertTrue(resultado.isPresent());
	}

	@Test(expected = ConsistenciaException.class)
	public void testListarCombustiveisNaoEncontrado() throws ConsistenciaException {

		cache.getCache("cacheListaCombustiveis").clear();

		BDDMockito.given(combustivelRepository.findAll()).willReturn(null);

		combustivelService.listar();
	}

	@Test
	public void testSalvar() throws ConsistenciaException {

		BDDMockito.given(combustivelRepository.save(Mockito.any(Combustivel.class))).willReturn(new Combustivel());

		Combustivel resultado = combustivelService.salvar(new Combustivel());

		assertNotNull(resultado);
	}
}