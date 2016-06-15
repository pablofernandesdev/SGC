package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Fornecedor;
import br.com.sgc.domain.Produto;




public interface ProdutoDao extends Serializable{

	public List<Produto> findProdutos(Produto produto);

	public void saveProduto(Produto produto);

	public void updateProduto(Produto produto);

	public void deleteProduto(Long idProduto);

	public List<Produto> findProdutoByCategoria(Long idCategoria);
	
	public List<Produto> findProdutosByFornecedor(Fornecedor fornecedor);

}
