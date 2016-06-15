package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.EntradaProdutoDao;
import br.com.sgc.dao.ProdutoDao;
import br.com.sgc.domain.EntradaProduto;
import br.com.sgc.domain.Fornecedor;
import br.com.sgc.domain.Produto;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.ProdutoService;

@Service("produtoServiceImpl")
public class ProdutoServiceImpl implements ProdutoService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntradaProdutoDao categoriaDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Override
	public List<Produto> findProdutos(Produto produto) {
		return produtoDao.findProdutos(produto);
	}

	@Override
	public void saveProduto(Produto produto) {
		produtoDao.saveProduto(produto);
	}

	@Override
	public void updateProduto(Produto produto) {
		produtoDao.updateProduto(produto);
	}
	
	@Override
	public void deleteProduto(Long idProduto) {
		List<EntradaProduto> listEntradaProduto = categoriaDao.findEntradaProdutoByProduto(idProduto);
		
		if(listEntradaProduto.size() > 0){
			throw new NegocioException("MSG_A004");
		}
		
		produtoDao.deleteProduto(idProduto);
	}

	@Override
	public List<Produto> findProdutosByFornecedor(Fornecedor fornecedor) {
		return produtoDao.findProdutosByFornecedor(fornecedor);
	}
	
}
