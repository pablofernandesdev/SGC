package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Fornecedor;


public interface FornecedorService extends Serializable {

	public List<Fornecedor> findFornecedores(Fornecedor fornecedor);

	public void saveFornecedor(Fornecedor fornecedor);

	public void updateFornecedor(Fornecedor fornecedor);

	public void deleteFornecedor(Long idFornecedor);
	
	public Fornecedor findFornecedorCnpj(String cnpj);
	
}