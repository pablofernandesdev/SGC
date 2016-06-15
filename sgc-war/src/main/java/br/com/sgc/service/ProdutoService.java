package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Fornecedor;
import br.com.sgc.domain.Produto;

public interface ProdutoService extends Serializable {

	/**
	 * Este metodo é responsável por retornar as produtos
	 * @param produto
	 * @return
	 */
	public List<Produto> findProdutos(Produto produto);

	public void saveProduto(Produto produto);

	public void updateProduto(Produto produto);

	public void deleteProduto(Long idProduto);
	
	public List<Produto> findProdutosByFornecedor(Fornecedor fornecedor);

}