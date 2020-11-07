package com.sdc.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "combustivel")
public class Combustivel {

	private static final long serialVersionUID = 1L;
	 
   	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
   	private int id;
   	
   	@Column(name = "tipo", nullable = false)
   	private int tipo;

   	@Column(name = "titulo", nullable = false)
   	private String titulo;
   	
   	@Column(name = "valor", nullable = false)
   	private double valor;

   	@Column(name = "data_Atualizacao", nullable = false)
   	private Date dataAtualizacao;
   	
   	public int getId() {
   		return this.id;
   	}
   	
   	public void setId(int id) {
   		this.id = id;
   	}
   	
   	public String getTitulo() {
   		return this.titulo;
   	}
   	
   	public void setTitulo(String titulo) {
   		this.titulo = titulo;
   	}
   	
   	public int getTipo() {
   		return this.tipo;
   	}
   	
   	public void setTipo(int tipo) {
   		this.tipo = tipo;
   	}
   	
   	public Double getValor() {
   		return this.valor;
   	}
   	
   	public void setValor(Double valor) {
   		this.valor = valor;
   	}
   	
   	public Date getDataAtualizacao() {
   		return this.dataAtualizacao;
   	}
   	
   	public void setDataAtualizacao (Date dataAtualizacao) {
   		this.dataAtualizacao = dataAtualizacao;
   	}

	@Override
	public String toString() {
		return "Combustivel[id=" + id + "," 
	            + "titulo=" + titulo + "," 
				+ "tipo=" + tipo + ","
				+ "valor=" + valor + "]";
	}
}
