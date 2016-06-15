package br.com.sgc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.sgc.util.SgcUtil;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProduto")
    private Long idProduto;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "marca")
	private String marca;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "margemLucro")
	private Double margemLucro;
	
	@Column(name = "precoVenda")
	private Double precoVenda;
	
	@Column(name = "quantidade")
	private Long quantidade;
	
	@Transient
    private String precoVendaFormatado;
	
	@Transient
    private String precoCustoFormatado;
	
    @Transient
    private String precoVendaComMascara;

	@Transient
	private String precoCustoComMascara;
	
	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="idCategoria") //persistente
	private Categoria categoria;

	@Column(name = "idCategoria", insertable= false, updatable= false) //nao persistente
	private Long idCategoria;
	
	@Column(name = "precoCusto")
	private Double precoCusto;
	
	@OneToMany (mappedBy="produto")
	private List<SaidaProduto> listaSaidaProduto; 
	
	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Double getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(Double margemLucro) {
		this.margemLucro = margemLucro;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(Double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public List<SaidaProduto> getListaSaidaProduto() {
		return listaSaidaProduto;
	}

	public void setListaSaidaProduto(List<SaidaProduto> listaSaidaProduto) {
		this.listaSaidaProduto = listaSaidaProduto;
	}
	
	public String getPrecoVendaComMascara() {
		return SgcUtil.formataMoeda(getPrecoVenda());
	}

	public void setPrecoVendaComMascara(String precoVendaComMascara) {
		this.precoVendaComMascara = precoVendaComMascara;
	}
	
	public String getPrecoCustoComMascara() {
		return SgcUtil.formataMoeda(getPrecoCusto());
	}

	public void setPrecoCustoComMascara(String precoCustoComMascara) {
		this.precoCustoComMascara = precoCustoComMascara;
	}
	
	public void setPrecoCustoFormatado(String valor){
		this.precoCusto = SgcUtil.stringToDouble(valor);
	}
	
	public String getPrecoCustoFormatado(){
		return precoCusto == null ? "" : SgcUtil.formataDoubleEmMacaraMonetario(precoCusto.doubleValue());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result
				+ ((idProduto == null) ? 0 : idProduto.hashCode());
		result = prime
				* result
				+ ((listaSaidaProduto == null) ? 0 : listaSaidaProduto
						.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result
				+ ((margemLucro == null) ? 0 : margemLucro.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((precoCusto == null) ? 0 : precoCusto.hashCode());
		result = prime * result
				+ ((precoVenda == null) ? 0 : precoVenda.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		if (listaSaidaProduto == null) {
			if (other.listaSaidaProduto != null)
				return false;
		} else if (!listaSaidaProduto.equals(other.listaSaidaProduto))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (margemLucro == null) {
			if (other.margemLucro != null)
				return false;
		} else if (!margemLucro.equals(other.margemLucro))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (precoCusto == null) {
			if (other.precoCusto != null)
				return false;
		} else if (!precoCusto.equals(other.precoCusto))
			return false;
		if (precoVenda == null) {
			if (other.precoVenda != null)
				return false;
		} else if (!precoVenda.equals(other.precoVenda))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}
	
	
}
