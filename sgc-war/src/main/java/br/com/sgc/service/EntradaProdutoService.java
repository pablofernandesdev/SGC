package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.EntradaProduto;

public interface EntradaProdutoService extends Serializable {

	public void saveEntradaProduto(EntradaProduto entradaProduto);

	public void deleteEntradaProduto(EntradaProduto entradaProduto);

	public List<EntradaProduto> findListaEntradaProdutoByProduto(Long idProduto);

	public List<EntradaProduto> findEntradaProdutos(EntradaProduto entradaProduto);
	
	

}