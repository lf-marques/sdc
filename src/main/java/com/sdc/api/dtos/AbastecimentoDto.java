package com.sdc.api.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AbastecimentoDto {

	private String id;
	
	@NotNull(message = "CombustivelId não pode ser vazio.")
	private String combustivelId;
	
	@NotNull(message = "CartaoId não pode ser vazio.")
	private String cartaoId;

	@NotNull(message = "Valor não pode ser vazio.")
	private String valor;

	@NotEmpty(message = "Confirmacao Pagamento não pode ser vazio.")
	@Length(min = 1, max = 1, message = "Confirmacao Pagamento precisar ser: 0/1")
	private String confirmacaoPagamento;
	
	@NotEmpty(message = "Confirmacao Abastecimento não pode ser vazio.")
	@Length(min = 1, max = 1, message = "Confirmacao Abastecimento precisar ser: 0/1")
	private String confirmacaoAbastecimento;

	@NotEmpty(message = "Placa não pode ser vazio.")
	@Length(min = 8, max = 8, message = "Placa de conter 8 caracteres.")
	private String placa;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}
	
	public String getCombustivelId() {
		return combustivelId;
	}

	public void setCombustivelId(String combustivelId) {
		this.combustivelId= combustivelId;
	}
	
	public String getCartaoId() {
		return cartaoId;
	}

	public void setCartaoId(String cartaoId) {
		this.cartaoId= cartaoId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getConfirmacaoPagamento() {
		return confirmacaoPagamento;
	}

	public void setConfirmacaoPagamento(String confirmacaoPagamento) {
		this.confirmacaoPagamento = confirmacaoPagamento;
	}

	public String getConfirmacaoAbastecimento() {
		return confirmacaoAbastecimento;
	}

	public void setConfirmacaoAbastecimento(String confirmacaoAbastecimento) {
		this.confirmacaoAbastecimento = confirmacaoAbastecimento;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "Abastecimento[id=" + id + "," 
	            + "combustivelId=" + combustivelId+ "," 
				+ "cartaoId=" + cartaoId + "," 
	            + "valor=" + valor + "," 
	    	    + "confirmacaoPagamento=" + confirmacaoPagamento + ","
	    	    + "confirmacaoAbastecimento=" + confirmacaoAbastecimento + ","
	    	    + "placa=" + placa + "]";
	}
}
