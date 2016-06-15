package br.com.sgc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.sgc.util.SgcUtil;

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario")
    private Long idUsuario;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "logradouro")
	private String logradouro;
	
	@Column(name = "nascimento")
	private Date nascimento;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "telCel")
	private String telCel;
	
	@Column(name = "telFixo")
	private String telFixo;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Transient
	private String cpfComMascara;
	
	@Transient
	private String telCelComMascara;
	
	@Transient
	private String telFixoComMascara;
	
	@Transient
	private String statusComMascara;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getCpfComMascara() {
		return SgcUtil.imprimeCPF(getCpf());
	}

	public void setCpfComMascara(String cpfComMascara) {
		this.cpfComMascara = cpfComMascara;
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
	
	public String getStatusComMascara() {
		return SgcUtil.imprimeStatus(getStatus());
	}

	public void setStatusComMascara(String statusComMascara) {
		this.statusComMascara = statusComMascara;
	}
	
}