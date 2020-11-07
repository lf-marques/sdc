package com.sdc.api.dtos;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class CombustivelDto {

	private String id;
	
	@NotEmpty(message = "Titulo não pode ser vazio")
	@Length(min = 1, max = 50, message = "Titulo deve conter de 1 a 50.")
	private String titulo;
	
	@NotEmpty(message = "Tipo não pode ser vazio.")
	private String tipo;

	@NotEmpty(message = "Valor não pode ser vazio")
	@Length(min = 1, max = 10, message = "Valor deve conter entre 1 e 10 caracteres númericos.")
	private String valor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}

	public String getTitulo() {
		return this.titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Combustivel["
				+ "id=" + id+ "," 
				+ "tipo=" + tipo+ "," 
				+ "valor=" + valor+ "]";
	}

}