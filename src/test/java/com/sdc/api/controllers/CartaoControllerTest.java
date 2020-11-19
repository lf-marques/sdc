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
import com.sdc.api.dtos.CartaoDto;
import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;
import com.sdc.api.services.CartaoService;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartaoControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CartaoService cartaoService;
	
	private Cartao CriarCartaoTeste() throws ParseException {

		Cliente cliente = new Cliente();

		cliente.setId(1);
		cliente.setSexo("M");
		cliente.setRg("058870980");
		cliente.setEmail("email@email.com");
		cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));

		Cartao cartaoTeste = new Cartao();
		List<Cartao> cartoes = new ArrayList<Cartao>();

		cartaoTeste.setId(1);
		cartaoTeste.setBloqueado(0);
		cartaoTeste.setDataValidade(new SimpleDateFormat("MM/yyyy").parse("01/2020"));
		cartaoTeste.setNumero("1234567891234567");
		cartoes.add(cartaoTeste);
		cartaoTeste.setCliente(cliente);
		
		return cartaoTeste;
	}
	
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testBuscarPorClienteIdExistente() throws Exception {
		
		List<Cartao> lst = new ArrayList<Cartao>();
		Cartao cartao = CriarCartaoTeste();
		lst.add(cartao);

		BDDMockito.given(cartaoService.buscarPorClienteId(Mockito.anyInt())).willReturn(Optional.of(lst));

		mvc.perform(MockMvcRequestBuilders.get("/api/cartao/cliente/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.[0].id").value(cartao.getId()))
				.andExpect(jsonPath("$.dados.[0].numero").value(cartao.getNumero()))
				.andExpect(jsonPath("$.dados.[0].dataValidade").value("Wed Jan 01 00:00:00 BRT 2020"))
				.andExpect(jsonPath("$.dados.[0].bloqueado").value(cartao.getBloqueado()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testBuscarPorClienteIdInexistente() throws Exception {

		BDDMockito.given(cartaoService.buscarPorClienteId((Mockito.anyInt())))
				.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/cartao/cliente/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.erros").value("Teste inconsistência"));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarComSucesso() throws Exception {
		
		Cartao cartao = CriarCartaoTeste();
		CartaoDto objEntrada = ConversaoUtils.Converter(cartao);
		objEntrada.setDataValidade("01/2020");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(cartaoService.salvar(Mockito.any(Cartao.class))).willReturn(cartao);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.id").value(cartao.getId()))
				.andExpect(jsonPath("$.dados.numero").value(cartao.getNumero()))
				.andExpect(jsonPath("$.dados.dataValidade").value("Wed Jan 01 00:00:00 BRT 2020"))
				.andExpect(jsonPath("$.dados.bloqueado").value(cartao.getBloqueado()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarSemSucesso() throws Exception {
		Cartao cartao = CriarCartaoTeste();
		CartaoDto objEntrada = ConversaoUtils.Converter(cartao);
		objEntrada.setDataValidade("01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(cartaoService.salvar(Mockito.any(Cartao.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarNumeroVazio() throws Exception {

		CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setClienteId("1");
		objEntrada.setBloqueado("0");
		objEntrada.setDataValidade("01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Númeo não pode ser vazio."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarNumeroExcedente() throws Exception {

		CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setClienteId("1");
		objEntrada.setBloqueado("0");
		objEntrada.setDataValidade("01/2020");
		objEntrada.setNumero("12345678915487625482");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Número deve conter 16 caracteres."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarDataDeValidadeVazia() throws Exception {
		CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setClienteId("1");
		objEntrada.setBloqueado("0");
		objEntrada.setNumero("5678915487625482");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Data validade não pode ser vazia."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarDataDeValidadeFormatoIncompativel() throws Exception {
		
			CartaoDto objEntrada = new CartaoDto();
			
			objEntrada.setClienteId("1");
			objEntrada.setBloqueado("0");
			objEntrada.setNumero("5678915487625482");
			objEntrada.setDataValidade("01/01/2020");

			String json = new ObjectMapper().writeValueAsString(objEntrada);

			mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.erros").value("Formato data de validade: mm/yyyy"));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarBloqueadoVazio() throws Exception {
		
		CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setClienteId("1");
		objEntrada.setNumero("5678915487625482");
		objEntrada.setDataValidade("01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Bloqueado não pode ser vazio."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarBloqueadoExcedente() throws Exception {
		
		CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setClienteId("1");
		objEntrada.setBloqueado("01");
		objEntrada.setNumero("5678915487625482");
		objEntrada.setDataValidade("01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Bloqueado deve ser 0/1"));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarClienteIdVazio() throws Exception {
CartaoDto objEntrada = new CartaoDto();
		
		objEntrada.setBloqueado("0");
		objEntrada.setNumero("5678915487625482");
		objEntrada.setDataValidade("01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cartao").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente ID não pode ser nulo"));
	}
}
