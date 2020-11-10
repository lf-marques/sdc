package com.sdc.api.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CartaoDto {
	
	private String id;

	@NotNull(message = "Númeo não pode ser vazio.")
	@Length(min = 16, max = 16, message = "Número deve conter 16 caracteres.")
	private String numero;

	@NotEmpty(message = "Data validade não pode ser vazia.")
	@Length(min = 7, max = 7, message = "Formato data de validade: mm/yyyy")
	private String dataValidade;

	@NotEmpty(message = "Bloqueado não pode ser vazio.")
	@Length(min = 1, max = 1, message = "Bloqueado deve ser 0/1")
	private String bloqueado;

	@NotEmpty(message = "Cliente ID não pode ser nulo")
	private String clienteId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	@Override
	public String toString() {
		return "Cartao[clienteId=" + clienteId + "," 
	            + "numero=" + numero + "," 
				+ "dataValidade=" + dataValidade + "," 
	            + "bloqueado=" + bloqueado + "]";
	}
}
