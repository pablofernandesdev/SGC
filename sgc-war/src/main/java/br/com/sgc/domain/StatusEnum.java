package br.com.sgc.domain;

public enum StatusEnum {
	
	 ADMINISTRADOR("A", "Administrador"), 
	 VENDEDOR("V", "Vendedor"); 
	 
	 private String sigla;
	 
	 private String descricao;
	 
	 StatusEnum(String sigla, String descricao){
		 this.sigla = sigla; //this referencia a variavel da classe
		 this.descricao = descricao;
	 }
	 
	 public String getSigla() {
		return sigla;
	}
	 
	 public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
 
}
