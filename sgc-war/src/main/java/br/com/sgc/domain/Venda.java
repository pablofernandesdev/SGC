package br.com.sgc.domain;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.sgc.util.SgcUtil;

@Entity
@Table(name = "Venda")
public class Venda {
	
	@Transient
	private String valorTotalComMascara;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idVenda")
    private Long idVenda;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataVenda")
	private Date dataVenda;
	
	@Column(name = "total")
	private Double total;
	
	@OneToMany (mappedBy="venda")
	private List<SaidaProduto> listaSaidaProduto; 
	
	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="idEmpresa")
	private Empresa empresa;

	@Column(name = "idEmpresa", insertable= false, updatable= false)
	private Long idEmpresa;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;

	@Column(name = "idUsuario", insertable= false, updatable= false)
	private Long idUsuario;
	
	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	@Column(name = "idCliente", insertable= false, updatable= false)
	private Long idCliente;
	
	@Transient
	private Date dataAtual;

	public Long getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public String getValorTotalComMascara() {
		return SgcUtil.formataMoeda(getTotal());
	}

	public void setValorTotalComMascara(String valorTotalComMascara) {
		this.valorTotalComMascara = valorTotalComMascara;
	}

	public List<SaidaProduto> getListaSaidaProduto() {
		return listaSaidaProduto;
	}

	public void setListaSaidaProduto(List<SaidaProduto> listaSaidaProduto) {
		this.listaSaidaProduto = listaSaidaProduto;
	}

	public Usuario getUsuario() { //usuario nao pode ser nulo
		if(usuario == null ){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Cliente getCliente() { //cliente nao pode ser nulo
		if(cliente == null ){
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	

}