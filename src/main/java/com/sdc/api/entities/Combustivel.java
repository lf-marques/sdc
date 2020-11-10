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
   	
   	public int getTipo() {
   		return this.tipo;
   	}
   	
   	public void setTipo(int tipo) {
   		this.tipo = tipo;
   	}
}
