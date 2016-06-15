package br.com.sgc.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.sgc.util.SgcUtil;

@Entity
@Table(name = "Empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEmpresa")
	private Long idEmpresa;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "email")
	private String email;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "nomeFantasia")
	private String nomeFantasia;

	@Column(name = "razaoSocial")
	private String razaoSocial;

	@Column(name = "telCel")
	private String telCel;

	@Column(name = "telFixo")
	private String telFixo;

	@Column(name = "uf")
	private String uf;

	@Transient
	private String cnpjComMascara;

	@Transient
	private String telCelComMascara;

	@Transient
	private String telFixoComMascara;

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTelCel() {
		return telCel;
	}

	public void setTelCel(String telCel) {
		this.telCel = telCel;
	}

	public String getTelFixo() {
		return telFixo;
	}

	public void setTelFixo(String telFixo) {
		this.telFixo = telFixo;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCnpjComMascara() {
		return SgcUtil.imprimeCNPJ(getCnpj());
	}

	public void setCnpjComMascara(String cnpjComMascara) {
		this.cnpjComMascara = cnpjComMascara;
	}

	public String getTelCelComMascara() {
		return SgcUtil.imprimeTelefone(getTelCel());
	}

	public void setTelCelComMascara(String telCelComMascara) {
		this.telCelComMascara = telCelComMascara;
	}

	public String getTelFixoComMascara() {
		return SgcUtil.imprimeTelefone(getTelFixo());
	}

	public void setTelFixoComMascara(String telFixoComMascara) {
		this.telFixoComMascara = telFixoComMascara;
	}

}