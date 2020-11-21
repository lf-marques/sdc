package com.sdc.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdc.api.dtos.CombustivelDto;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.services.CombustivelService;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CombustivelControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CombustivelService combustivelService;
	
	Combustivel combustivelTeste = new Combustivel();
	
	private Combustivel CriarCombustivelTestes() throws ParseException {

		combustivelTeste = new Combustivel();

		combustivelTeste.setId(1);
		combustivelTeste.setTipo(1);
		combustivelTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		combustivelTeste.setTitulo("Gososa");
		combustivelTeste.setValor(200.00);
		
		return combustivelTeste;
	}
	@Test
	@WithMockUser
	public void testBuscarPorTipoComSucesso() throws Exception {
		
		Combustivel combustivel = CriarCombustivelTestes();

		BDDMockito.given(combustivelService.buscarPorTipo(Mockito.anyInt())).willReturn(Optional.of(combustivel));

		mvc.perform(MockMvcRequestBuilders.get("/api/combustivel/tipo/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.id").value(combustivel.getId()))
				.andExpect(jsonPath("$.dados.tipo").value(combustivel.getTipo()))
				.andExpect(jsonPath("$.dados.valor").value(combustivel.getValor()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser
	public void testBuscarPorTipoSemSucesso() throws Exception{

		BDDMockito.given(combustivelService.buscarPorTipo((Mockito.anyInt())))
				.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/combustivel/tipo/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}

	@Test
	@WithMockUser
	public void testListarCombustiveisComSucesso() throws Exception {

		List<Combustivel> lstCombustivel = new ArrayList<Combustivel>();
		lstCombustivel.add(CriarCombustivelTestes());

		BDDMockito.given(combustivelService.listar()).willReturn(Optional.of(lstCombustivel));

		mvc.perform(MockMvcRequestBuilders.get("/api/combustivel/listar").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.[0].id").value(lstCombustivel.get(0).getId()))
				.andExpect(jsonPath("$.dados.[0].tipo").value(lstCombustivel.get(0).getTipo()))
				.andExpect(jsonPath("$.dados.[0].valor").value(lstCombustivel.get(0).getValor()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser
	public void testListarCombustiveisSemSucesso() throws Exception {

		BDDMockito.given(combustivelService.listar())
				.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/combustivel/listar").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarComSucesso() throws Exception {
		
		Combustivel combustivel = CriarCombustivelTestes();
		CombustivelDto objEntrada = ConversaoUtils.Converter(combustivel);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(combustivelService.salvar(Mockito.any(Combustivel.class))).willReturn(combustivel);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.id").value(combustivel.getId()))
				.andExpect(jsonPath("$.dados.tipo").value(combustivel.getTipo()))
				.andExpect(jsonPath("$.dados.valor").value(combustivel.getValor()))
				.andExpect(jsonPath("$.dados.titulo").value(combustivel.getTitulo()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarSemSucesso() throws Exception {

		Combustivel combustivel = CriarCombustivelTestes();
		CombustivelDto objEntrada = ConversaoUtils.Converter(combustivel);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(combustivelService.salvar(Mockito.any(Combustivel.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarTituloVazio() throws Exception {
		
		CombustivelDto objEntrada = new CombustivelDto();

		objEntrada.setTipo("1");
		objEntrada.setValor("200.00");;
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Titulo não pode ser vazio"));
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarTituloExcedente() throws Exception {
		
		CombustivelDto objEntrada = new CombustivelDto();

		objEntrada.setTipo("1");
		objEntrada.setTitulo("diam volutpat commodo sed egestas egestas fringilla phasellus faucibus scelerisque");
		objEntrada.setValor("200.00");;
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Titulo deve conter de 1 a 50."));
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarTipoVazio() throws Exception {
		
		CombustivelDto objEntrada = new CombustivelDto();
		
		objEntrada.setTitulo("Gasosa");
		objEntrada.setValor("200.00");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Tipo não pode ser vazio."));
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarValorVazio() throws Exception {
		
		CombustivelDto objEntrada = new CombustivelDto();
		
		objEntrada.setTipo("1");
		objEntrada.setTitulo("Gasosa");	

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Valor não pode ser vazio"));
	}
	@Test
	@WithMockUser(roles = "FUNCIONARIO")
	public void testSalvarValorExcedente() throws Exception {
		
		CombustivelDto objEntrada = new CombustivelDto();
		
		objEntrada.setTipo("1");
		objEntrada.setTitulo("Gasosa");
		objEntrada.setValor("200000000000000");		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/combustivel").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Valor deve conter entre 1 e 10 caracteres númericos."));
	}
}
