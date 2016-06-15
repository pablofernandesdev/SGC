package br.com.sgc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Entradaproduto")
public class EntradaProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEntradaProduto")
	private Long idEntradaProduto;

	@Column(name = "data")
	private Date data;

	@Column(name = "quantidadeEntrada")
	private Long quantidadeEntrada;

	// bi-directional many-to-one association to Produto
	@ManyToOne
	@JoinColumn(name = "idProduto")
	private Produto produto;

	@Column(name = "idProduto", insertable = false, updatable = false)
	private Long idProduto;

	// bi-directional many-to-one association to Fornecedor
	@ManyToOne
	@JoinColumn(name = "idFornecedor")
	private Fornecedor fornecedor;
	
	@Column(name = "idFornecedor", insertable = false, updatable = false)
	private Long idFornecedor;

	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}

	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getQuantidadeEntrada() {
		return quantidadeEntrada;
	}

	public void setQuantidadeEntrada(Long quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Fornecedor getFornecedor() {
		if(fornecedor == null ){
			fornecedor = new Fornecedor();
		}
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

}
