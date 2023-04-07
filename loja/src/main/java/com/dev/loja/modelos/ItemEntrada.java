package com.dev.loja.modelos;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="item_entrada")
public class ItemEntrada implements Serializable{
	
//	public Funcionario() {
//		super();
//	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Entrada entrada;
	@ManyToOne
	private Produto produto;
	private Double quantidade= 0.00;
	private Double valorProduto= 0.00;
	private Double valorVenda= 0.00;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Entrada getEntrada() {
		return entrada;
	}
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(Double valorProduto) {
		this.valorProduto = valorProduto;
	}
	public Double getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	
	
}	
