package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.CategoriaDao;
import br.com.sgc.dao.ProdutoDao;
import br.com.sgc.domain.Categoria;
import br.com.sgc.domain.Produto;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.CategoriaService;

@Service("categoriaServiceImpl")
public class CategoriaServiceImpl implements CategoriaService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Override
	public List<Categoria> findCategorias(Categoria categoria) {
		return categoriaDao.findCategorias(categoria);
	}

	@Override
	public void saveCategoria(Categoria categoria) {
		categoriaDao.saveCategoria(categoria);
	}

	@Override
	public void updateCategoria(Categoria categoria) {
		categoriaDao.updateCategoria(categoria);
	}
	
	@Override
	public void deleteCategoria(Long idCategoria) {
		List<Produto> listProduto = produtoDao.findProdutoByCategoria(idCategoria);
		
		if(listProduto.size() > 0){
			throw new NegocioException("MSG_A020");
		}
		
		categoriaDao.deleteCategoria(idCategoria);
	}
	
}
