package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.EntradaProdutoDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.EntradaProduto;

@Repository("entradaProdutoDaoImpl")   
public class EntradaProdutoDaoImpl implements EntradaProdutoDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<EntradaProduto> daoEntradaProduto;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntradaProduto> findListaEntradaProdutoByProduto(Long idProduto) {
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ep ");
		hql.append(" FROM EntradaProduto ep ");
		hql.append(" WHERE ep.idProduto =  "+ idProduto );
		
		Query query = daoEntradaProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<EntradaProduto>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntradaProduto> findEntradaProdutoByFornecedor(Long idFornecedor){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" pro ");
		hql.append(" FROM EntradaProduto pro ");
		hql.append(" inner join pro.fornecedor cat ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND cat.idFornecedor =  " + idFornecedor);
				
		Query query = daoEntradaProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<EntradaProduto>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntradaProduto> findEntradaProdutoByProduto(Long idProduto){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ent ");
		hql.append(" FROM EntradaProduto ent ");
		hql.append(" inner join ent.produto pro ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND pro.idProduto =  " + idProduto);
				
		Query query = daoEntradaProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<EntradaProduto>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntradaProduto> findEntradaProdutos(EntradaProduto entradaProduto) {
		
		StringBuilder hql = new StringBuilder();
		

		hql.append(" SELECT ");
		hql.append(" ent ");
		hql.append(" FROM EntradaProduto ent ");
		
		hql.append(" where 1 = 1 ");
		
		if(entradaProduto.getIdEntradaProduto() != null){
			hql.append(" AND ent.idEntradaProduto =  " + entradaProduto.getIdEntradaProduto());
		}else{
		
			if(entradaProduto.getData() != null){
				hql.append(" AND ent.data =  '" + entradaProduto.getData() +"'");
			}
			
			if(entradaProduto.getIdProduto() != null){
				hql.append(" AND ent.idProduto =  '" + entradaProduto.getIdProduto() +"'");
			}
			
			if(entradaProduto.getIdFornecedor() != null){
				hql.append(" AND ent.idFornecedor =  '" + entradaProduto.getIdFornecedor() +"'");
			}
			
			if(entradaProduto.getFornecedor().getRazaoSocial()!= null && !entradaProduto.getFornecedor().getRazaoSocial().isEmpty()){
				hql.append(" AND ent.fornecedor.razaoSocial like  '%" + entradaProduto.getFornecedor().getRazaoSocial() +"%'");
			}
			
		}
							
		Query query = daoEntradaProduto.getEntityManager().createQuery(hql.toString());
		
		return (List<EntradaProduto>) query.getResultList();
	}
	
}

