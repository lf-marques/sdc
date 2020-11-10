package com.sdc.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "abastecimento")
public class Abastecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "confirmacao_Pagamento", nullable = false)
	private int confirmacaoPagamento;
	
	@Column(name = "confirmacao_Abastecimento", nullable = false)
	private int confirmacaoAbastecimento;
	
	@Column(name = "data_Validade", nullable = false)
	private Date dataValidade;
	
	@Column(name = "placa", nullable = false, length = 8)
	private String placa;
	
	@OneToOne(fetch = FetchType.EAGER)
   	private Cartao cartao;
	
	@OneToOne(fetch = FetchType.EAGER)
   	private Combustivel combustivel;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public int getConfirmacaoPagamento() {
		return this.confirmacaoPagamento;
	}
	
	public void setConfirmacaoPagamento(int confirmacaoPagamento) {
		this.confirmacaoPagamento = confirmacaoPagamento;
	}

	public int getConfirmacaoAbastecimento() {
		return this.confirmacaoAbastecimento;
	}
	
	public void setConfirmacaoAbastecimento(int confirmacaoAbastecimento) {
		this.confirmacaoAbastecimento = confirmacaoAbastecimento;
	}
}
