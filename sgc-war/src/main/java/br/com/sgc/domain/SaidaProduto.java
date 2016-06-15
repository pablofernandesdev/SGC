package br.com.sgc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.sgc.util.SgcUtil;

@Entity
@Table(name = "SaidaProduto")
public class SaidaProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSaidaProduto")
    private Long idSaidaProduto;

	@Column(name = "quantidadeSaida")
	private Long quantidadeSaida;
	
	@Column(name = "data")
	private Date data;
		
	@ManyToOne(fetch = FetchType.LAZY) //NAO CARREGAR A LISTA
	@JoinColumn(name = "venda_idVenda")
	private Venda venda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "produto_idProduto")
	private Produto produto;
	
	@Column(name = "precoVendaSaida")
	private Double precoVendaSaida;
	
	@Transient
	private Double totalSomado;
	
	@Transient
	private String totalSomadoComMascara;
	
	@Transient
	private String precoVendaSaidaComMascara;

	public Long getIdSaidaProduto() {
		return idSaidaProduto;
	}

	public void setIdSaidaProduto(Long idSaidaProduto) {
		this.idSaidaProduto = idSaidaProduto;
	}

	public Long getQuantidadeSaida() {
		return quantidadeSaida;
	}

	public void setQuantidadeSaida(Long quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getTotalSomado() {
		if(totalSomado == null){
			totalSomado = 0D;
		}
		return totalSomado;
	}

	public void setTotalSomado(Double totalSomado) {
		this.totalSomado = totalSomado;
	}
	
	public String getTotalSomadoComMascara() {
		return SgcUtil.formataMoeda(getTotalSomado());
	}

	public void setTotalSomadoComMascara(String totalSomadoComMascara) {
		this.totalSomadoComMascara = totalSomadoComMascara;
	}

	public Double getPrecoVendaSaida() {
		return precoVendaSaida;
	}

	public void setPrecoVendaSaida(Double precoVendaSaida) {
		this.precoVendaSaida = precoVendaSaida;
	}
	
	public String getPrecoVendaSaidaComMascara() {
		return SgcUtil.formataMoeda(getPrecoVendaSaida());
	}

	public void setPrecoVendaSaidaComMascara(String precoVendaSaidaComMascara) {
		this.precoVendaSaidaComMascara = precoVendaSaidaComMascara;
	}
	
}
