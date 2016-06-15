package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.EntradaProduto;

public interface EntradaProdutoDao extends Serializable  {
	
	public List<EntradaProduto> findListaEntradaProdutoByProduto(Long idProduto);

	public List<EntradaProduto> findEntradaProdutoByProduto(Long idProduto);

	public List<EntradaProduto> findEntradaProdutos(EntradaProduto entradaProduto);

	public List<EntradaProduto> findEntradaProdutoByFornecedor(Long idFornecedor);
}
