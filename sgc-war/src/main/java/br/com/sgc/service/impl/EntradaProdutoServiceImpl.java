package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.EntradaProdutoDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.EntradaProduto;
import br.com.sgc.domain.Produto;
import br.com.sgc.service.EntradaProdutoService;

@SuppressWarnings("serial")
@Service("entradaProdutoServiceImpl")
public class EntradaProdutoServiceImpl implements EntradaProdutoService{

	@Autowired
	private Dao<EntradaProduto> daoEntradaProduto;
	
	@Autowired
	private EntradaProdutoDao entradaProdutoDao;
	
	@Autowired
	private Dao<Produto> daoProduto;
	
	@Override
	public void saveEntradaProduto(EntradaProduto entradaProduto) {
		
		/** cria a entrada com a quantidade **/
		daoEntradaProduto.setPersistentClass(EntradaProduto.class);
		daoEntradaProduto.save(entradaProduto);
		
		Long totalGeral = entradaProduto.getQuantidadeEntrada().longValue() + entradaProduto.getProduto().getQuantidade().longValue();
		
		atualizarQtdProduto(totalGeral, entradaProduto.getProduto().getIdProduto());
		
	}
	
	@Override
	public void deleteEntradaProduto(EntradaProduto entradaProduto) {

		daoEntradaProduto.setPersistentClass(EntradaProduto.class);
		daoEntradaProduto.delete(entradaProduto.getIdEntradaProduto());

		Long totalGeral =  entradaProduto.getProduto().getQuantidade().longValue() - entradaProduto.getQuantidadeEntrada().longValue();
		
		atualizarQtdProduto(totalGeral, entradaProduto.getProduto().getIdProduto());
		
	}
	
	private void atualizarQtdProduto(Long qtd, Long idProduto){
		daoProduto.setPersistentClass(Produto.class);
		Produto prod = daoProduto.findById(idProduto);
		prod.setQuantidade(qtd);
		
		daoProduto.save(prod);
	}

	@Override
	public List<EntradaProduto> findListaEntradaProdutoByProduto(Long idProduto) {
		
		return entradaProdutoDao.findListaEntradaProdutoByProduto(idProduto);
	}
	
	@Override
	public List<EntradaProduto> findEntradaProdutos(EntradaProduto entradaProduto) {
		
		return entradaProdutoDao.findEntradaProdutos(entradaProduto);
	}
	
}
