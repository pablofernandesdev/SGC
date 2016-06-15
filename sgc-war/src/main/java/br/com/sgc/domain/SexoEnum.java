package br.com.sgc.domain;

public enum SexoEnum {
	
	 MASCULINO("M", "Masculino"), 
	 FEMININO("F", "Feminino"); 
	 
	 private String sigla;
	 
	 private String descricao;
	 
	 SexoEnum(String sigla, String descricao){
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
