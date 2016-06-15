package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Fornecedor;

public interface FornecedorDao extends Serializable{

	public List<Fornecedor> findFornecedores(Fornecedor fornecedor);

	public void saveFornecedor(Fornecedor fornecedor);

	public void updateFornecedor(Fornecedor fornecedor);

	public void deleteFornecedor(Long idFornecedor);

	public Fornecedor findFornecedorCnpj(String cnpj);

}
