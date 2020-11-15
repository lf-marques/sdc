package com.sdc.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sdc.api.dtos.AbastecimentoDto;
import com.sdc.api.dtos.CartaoDto;
import com.sdc.api.entities.Abastecimento;
import com.sdc.api.entities.Cartao;
import com.sdc.api.dtos.RegraDto;
import com.sdc.api.entities.Regra;
import com.sdc.api.controllers.UsuarioController;
import com.sdc.api.dtos.ClienteDto;
import com.sdc.api.dtos.CombustivelDto;
import com.sdc.api.dtos.EnderecoDto;
import com.sdc.api.dtos.UsuarioCadastroDto;
import com.sdc.api.dtos.UsuarioDto;
import com.sdc.api.entities.Usuario;
import com.sdc.api.services.CombustivelService;
import com.sdc.api.services.UsuarioService;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.entities.Endereco;

public class ConversaoUtils {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private static UsuarioService usuarioService;

	public static Usuario Converter(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {

		Usuario usuario = new Usuario();

		if (usuarioCadastroDto.getId() != null && usuarioCadastroDto.getId() != "")
			usuario.setId(Integer.parseInt(usuarioCadastroDto.getId()));

		usuario.setCpf(usuarioCadastroDto.getCpf());
		usuario.setNome(usuarioCadastroDto.getNome());
		usuario.setSenha(usuarioCadastroDto.getSenha());
		usuario.setTipo(Integer.parseInt(usuarioCadastroDto.getTipo()));

		if (usuarioCadastroDto.getRegras() != null && usuarioCadastroDto.getRegras().size() > 0) {

			usuario.setRegras(new ArrayList<Regra>());

			for (RegraDto regraDto : usuarioCadastroDto.getRegras()) {

				Regra regra = new Regra();
				regra.setNome(regraDto.getNome());

				usuario.getRegras().add(regra);

			}

		}

		return usuario;
	}

	public static Cliente ConverterUsuarioCliente(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {

		SimpleDateFormat dateParse = new SimpleDateFormat("dd/MM/yyyy");

		Usuario usuario = Converter(usuarioCadastroDto);
		Cliente cliente = new Cliente();

		cliente.setEmail(usuarioCadastroDto.getCliente().getEmail());
		cliente.setDataNascimento(dateParse.parse(usuarioCadastroDto.getCliente().getDataNascimento()));
		cliente.setRg(usuarioCadastroDto.getCliente().getRg());
		cliente.setSexo(usuarioCadastroDto.getCliente().getSexo());

		cliente.setUsuario(usuario);
		return cliente;
	}

	public static Endereco ConverterEndereco(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {

		Endereco endereco = new Endereco();
		Cliente cliente = ConverterUsuarioCliente(usuarioCadastroDto);

		endereco.setCidade(usuarioCadastroDto.getCliente().getEndereco().getCidade());
		endereco.setNumero(Integer.parseInt(usuarioCadastroDto.getCliente().getEndereco().getNumero()));
		endereco.setRua(usuarioCadastroDto.getCliente().getEndereco().getRua());
		endereco.setUf(usuarioCadastroDto.getCliente().getEndereco().getUf());
		endereco.setCliente(cliente);

		return endereco;
	}

	public static UsuarioCadastroDto ConverterUsuarioCadastro(Usuario usuario) {

		UsuarioCadastroDto usuarioCadastroDto = new UsuarioCadastroDto();

		usuarioCadastroDto.setCpf(usuario.getCpf());
		usuarioCadastroDto.setNome(usuario.getNome());
		usuarioCadastroDto.setSenha(usuario.getSenha());
		usuarioCadastroDto.setTipo(String.valueOf(usuario.getTipo()));

		if (usuario.getRegras() != null) {

			usuarioCadastroDto.setRegras(new ArrayList<RegraDto>());

			for (int i = 0; i < usuario.getRegras().size(); i++) {

				RegraDto regraDto = new RegraDto();

				regraDto.setNome(usuario.getRegras().get(i).getNome());
				regraDto.setDescricao(usuario.getRegras().get(i).getDescricao());
				regraDto.setAtivo(usuario.getRegras().get(i).getAtivo());

				usuarioCadastroDto.getRegras().add(regraDto);

			}

		}

		return usuarioCadastroDto;
	}

	public static UsuarioDto Converter(Usuario usuario) {

		UsuarioDto usuarioDto = new UsuarioDto();

		if (usuario.getId() > 0)
			usuarioDto.setId(String.valueOf(usuario.getId()));

		usuarioDto.setCpf(usuario.getCpf());
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setTipo(String.valueOf(usuario.getTipo()));

		if (usuario.getRegras() != null) {

			usuarioDto.setRegras(new ArrayList<RegraDto>());

			for (int i = 0; i < usuario.getRegras().size(); i++) {

				RegraDto regraDto = new RegraDto();

				regraDto.setNome(usuario.getRegras().get(i).getNome());
				regraDto.setDescricao(usuario.getRegras().get(i).getDescricao());
				regraDto.setAtivo(usuario.getRegras().get(i).getAtivo());

				usuarioDto.getRegras().add(regraDto);

			}

		}

		return usuarioDto;
	}
	
	public static Usuario Converter(UsuarioDto usuarioDto) throws ConsistenciaException {

		Usuario usuario = new Usuario();
		
		if (usuarioDto.getId() != null && usuarioDto.getId() != "")
			usuario.setId(Integer.parseInt(usuarioDto.getId()));

		usuario.setCpf(usuarioDto.getCpf());
		usuario.setNome(usuarioDto.getNome());
		usuario.setTipo(Integer.parseInt(usuarioDto.getTipo()));
		usuario.setSenha(usuarioDto.getSenha());

		if (usuarioDto.getRegras() != null) {

			usuario.setRegras(new ArrayList<Regra>());

			for (int i = 0; i < usuarioDto.getRegras().size(); i++) {

				Regra regra = new Regra();

				regra.setNome(usuarioDto.getRegras().get(i).getNome());
				regra.setDescricao(usuarioDto.getRegras().get(i).getDescricao());
				regra.setAtivo(usuarioDto.getRegras().get(i).getAtivo());

				usuario.getRegras().add(regra);

			}

		}

		return usuario;
	}


	public static CartaoDto Converter(Cartao cartao) {

		CartaoDto cartaoDto = new CartaoDto();

		cartaoDto.setId(String.valueOf(cartao.getId()));
		cartaoDto.setNumero(cartao.getNumero());
		cartaoDto.setDataValidade(cartao.getDataValidade().toString());
		cartaoDto.setBloqueado(String.valueOf(cartao.getBloqueado()));
		cartaoDto.setClienteId(String.valueOf(cartao.getCliente().getId()));

		return cartaoDto;
	}

	public static Cartao Converter(CartaoDto cartaoDto) throws ParseException {
		Cartao cartao = new Cartao();

		if (cartaoDto.getId() != null && cartaoDto.getId() != "")
			cartao.setId(Integer.parseInt(cartaoDto.getId()));

		cartao.setNumero(cartaoDto.getNumero());
		cartao.setDataValidade(new SimpleDateFormat("MM/yyyy").parse(cartaoDto.getDataValidade()));
		cartao.setBloqueado(Integer.parseInt(cartaoDto.getBloqueado()));

		Cliente cliente = new Cliente();
		cliente.setId(Integer.parseInt(cartaoDto.getClienteId()));

		cartao.setCliente(cliente);

		return cartao;
	}

	public static List<CartaoDto> ConverterLista(List<Cartao> lista) {

		List<CartaoDto> lst = new ArrayList<CartaoDto>(lista.size());

		for (Cartao cartao : lista) {
			lst.add(Converter(cartao));
		}

		return lst;
	}
	
	public static List<CombustivelDto> ConverterListaCombustivel(List<Combustivel> lista) {

		List<CombustivelDto> lst = new ArrayList<CombustivelDto>(lista.size());

		for (Combustivel combustivel : lista) {
			lst.add(Converter(combustivel));
		}

		return lst;
	}

	public static Cliente Converter(ClienteDto clienteDto) throws ParseException {

		Usuario usuario = new Usuario();
		Cliente cliente = new Cliente();
		Endereco endereco = new Endereco();
		List<Endereco> enderecos = new ArrayList<Endereco>();

		if (clienteDto.getId() != null && clienteDto.getId() != "")
			cliente.setId(Integer.parseInt(clienteDto.getId()));

		if (clienteDto.getUsuarioId() != null && clienteDto.getUsuarioId() != "")
			usuario.setId(Integer.parseInt(clienteDto.getUsuarioId()));

		if (clienteDto.getEndereco().getId() != null && clienteDto.getEndereco().getId() != "")
			endereco.setId(Integer.parseInt(clienteDto.getEndereco().getId()));

		cliente.setEmail(clienteDto.getEmail());
		cliente.setDataNascimento(new SimpleDateFormat("dd/mm/yyyy").parse(clienteDto.getDataNascimento()));
		cliente.setRg(clienteDto.getRg());
		cliente.setSexo(clienteDto.getSexo());

		endereco.setCliente(cliente);
		endereco.setCidade(clienteDto.getEndereco().getCidade());
		endereco.setNumero(Integer.parseInt(clienteDto.getEndereco().getNumero()));
		endereco.setRua(clienteDto.getEndereco().getRua());
		endereco.setUf(clienteDto.getEndereco().getUf());
		enderecos.add(endereco);

		cliente.setUsuario(usuario);
		cliente.setEnderecos(enderecos);
		return cliente;
	}

	public static ClienteDto Converter(Cliente cliente) {

		Endereco endereco = cliente.getEnderecos().get(0);
		ClienteDto clienteDto = new ClienteDto();
		EnderecoDto enderedoDto = new EnderecoDto();

		if (cliente.getId() > 0)
			clienteDto.setId(String.valueOf(cliente.getId()));
		clienteDto.setUsuarioId(String.valueOf(cliente.getUsuario().getId()));

		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setDataNascimento(String.valueOf(cliente.getDataNascimento()));
		clienteDto.setRg(cliente.getRg());
		clienteDto.setSexo(cliente.getSexo());

		if (endereco.getId() > 0) {
			enderedoDto.setId(String.valueOf(endereco.getId()));
			enderedoDto.setCidade(endereco.getCidade());
			enderedoDto.setNumero(String.valueOf(endereco.getNumero()));
			enderedoDto.setRua(endereco.getRua());
			enderedoDto.setUf(endereco.getUf());
			enderedoDto.setRua(endereco.getRua());

			clienteDto.setEndereco(enderedoDto);
		}

		return clienteDto;
	}

	public static Combustivel Converter(CombustivelDto combustivelDto) throws ParseException {

		Combustivel combustivel = new Combustivel();

		if (combustivelDto.getId() != null && combustivelDto.getId() != "")
			combustivel.setId(Integer.parseInt(combustivelDto.getId()));

		combustivel.setTitulo(combustivelDto.getTitulo());
		combustivel.setTipo(Integer.parseInt(combustivelDto.getTipo()));
		combustivel.setValor(Double.valueOf(combustivelDto.getValor()));

		return combustivel;
	}

	public static CombustivelDto Converter(Combustivel combustivel) {

		CombustivelDto combustivelDto = new CombustivelDto();

		if (combustivel.getId() > 0)
			combustivelDto.setId(String.valueOf(combustivel.getId()));

		combustivelDto.setTitulo(combustivel.getTitulo());
		combustivelDto.setTipo(String.valueOf(combustivel.getTipo()));
		combustivelDto.setValor(String.valueOf(combustivel.getValor()));

		return combustivelDto;
	}

	public static Abastecimento Converter(AbastecimentoDto abastecimentoDto) throws ParseException {

		Abastecimento abastecimento = new Abastecimento();
		Combustivel combustivel = new Combustivel();
		Cartao cartao = new Cartao();

		if (abastecimentoDto.getId() != null && abastecimentoDto.getId() != "")
			abastecimento.setId(Integer.parseInt(abastecimentoDto.getId()));

		abastecimento.setConfirmacaoAbastecimento(Integer.parseInt(abastecimentoDto.getConfirmacaoAbastecimento()));
		abastecimento.setConfirmacaoPagamento(Integer.parseInt(abastecimentoDto.getConfirmacaoPagamento()));
		abastecimento.setValor(Double.parseDouble(abastecimentoDto.getValor()));
		abastecimento.setPlaca(abastecimentoDto.getPlaca());

		combustivel.setId(Integer.parseInt(abastecimentoDto.getCombustivelId()));
		cartao.setId(Integer.parseInt(abastecimentoDto.getCartaoId()));

		abastecimento.setCombustivel(combustivel);
		abastecimento.setCartao(cartao);

		return abastecimento;
	}

	public static AbastecimentoDto Converter(Abastecimento abastecimento) {

		AbastecimentoDto abastecimentoDto = new AbastecimentoDto();

		if (abastecimento.getId() > 0)
			abastecimentoDto.setId(String.valueOf(abastecimento.getId()));

		abastecimentoDto.setConfirmacaoAbastecimento(String.valueOf(abastecimento.getConfirmacaoAbastecimento()));
		abastecimentoDto.setConfirmacaoPagamento(String.valueOf(abastecimento.getConfirmacaoPagamento()));
		abastecimentoDto.setValor(String.valueOf(abastecimento.getValor()));
		abastecimentoDto.setPlaca(abastecimento.getPlaca());
		abastecimentoDto.setCombustivelId(String.valueOf(abastecimento.getCombustivel().getId()));
		abastecimentoDto.setCartaoId(String.valueOf(abastecimento.getCartao().getId()));

		return abastecimentoDto;
	}
}
