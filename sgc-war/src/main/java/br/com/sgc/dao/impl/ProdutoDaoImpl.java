package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.ProdutoDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Categoria;
import br.com.sgc.domain.Fornecedor;
import br.com.sgc.domain.Produto;

@Repository("produtoDaoImpl")
public class ProdutoDaoImpl implements ProdutoDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Produto> daoProduto;
	
	@Autowired
	private Dao<Categoria> daoCategoria;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findProdutos(Produto produto) {
		
		StringBuilder hql = new StringBuilder();
		
		/**
		 * consulta feita em HQL -- Referencia as classes e seus relacionamentos
		 * */
		
		hql.append(" SELECT ");
		hql.append(" pro ");
		hql.append(" FROM Produto pro ");
		hql.append(" where 1 = 1 ");
		
		if(produto.getIdProduto() != null){
			hql.append(" AND pro.idProduto =  " + produto.getIdProduto());
		}else{
		
			if(produto.getDescricao() != null && !produto.getDescricao().isEmpty()){
				hql.append(" AND pro.descricao like  '%" + produto.getDescricao().trim() +"%'");
			}
			
			if(produto.getNome() != null && !produto.getNome().isEmpty()){
				hql.append(" AND pro.nome like  '%" + produto.getNome().trim() +"%'");
			}
			
			if(produto.getMarca() != null && !produto.getMarca().isEmpty()){
				hql.append(" AND pro.marca like  '%" + produto.getMarca().trim() +"%'");
			}
			
			hql.append(" order by pro.idProduto desc " );
		}
							
		Query query = daoProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<Produto>) query.getResultList();
	}
	
	@Override
	public void saveProduto(Produto produto) {
		daoProduto.setPersistentClass(Produto.class);
		daoCategoria.setPersistentClass(Categoria.class);
		produto.setQuantidade(0L);//a quantidade nao e informada ao cadastrar produto
		produto.setCategoria(daoCategoria.findById(produto.getIdCategoria()));
		daoProduto.save(produto);
	}
	
	@Override
	public void updateProduto(Produto produto) {
		daoProduto.setPersistentClass(Produto.class);
		daoCategoria.setPersistentClass(Categoria.class);
		produto.setCategoria(daoCategoria.findById(produto.getIdCategoria()));
		daoProduto.update(produto);
	}
	
	@Override
	public void deleteProduto(Long idProduto) {
		daoProduto.setPersistentClass(Produto.class);
		daoProduto.delete(idProduto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findProdutoByCategoria(Long idCategoria){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" pro ");
		hql.append(" FROM Produto pro ");
		hql.append(" inner join pro.categoria cat ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND cat.idCategoria =  " + idCategoria);
				
		Query query = daoProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<Produto>) query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findProdutosByFornecedor(Fornecedor fornecedor) {
		
		StringBuilder hql = new StringBuilder();
		

		hql.append(" SELECT ");
		hql.append(" distinct prod ");
		hql.append(" FROM EntradaProduto ent ");
		hql.append(" inner join ent.produto prod ");
		hql.append(" where 1 = 1 ");
		
		if(fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()){
			hql.append(" AND ent.fornecedor.cnpj =  '" + fornecedor.getCnpj() +"'");
		}
		
		if(fornecedor.getRazaoSocial()!= null && !fornecedor.getRazaoSocial().isEmpty()){
			hql.append(" AND ent.fornecedor.razaoSocial like  '%" + fornecedor.getRazaoSocial() +"%'");
		}
			
		Query query = daoProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<Produto>) query.getResultList();
	}
	
}

