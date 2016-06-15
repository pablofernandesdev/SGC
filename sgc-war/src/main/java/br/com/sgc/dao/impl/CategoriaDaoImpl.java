package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.CategoriaDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Categoria;


@Repository("categoriaDaoImpl")
public class CategoriaDaoImpl implements CategoriaDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Categoria> daoCategoria;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findCategorias(Categoria categoria) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" cat ");
		hql.append(" FROM Categoria cat ");
		hql.append(" where 1 = 1 ");
		
		if(categoria != null){
			if(categoria.getIdCategoria() != null){
				hql.append(" AND cat.idCategoria =  " + categoria.getIdCategoria());
			}
			
			if(categoria.getNome() != null && !categoria.getNome().isEmpty()){
				hql.append(" AND cat.nome like  '%" + categoria.getNome().trim() +"%'");
			}
			
			hql.append(" order by cat.idCategoria desc " );
		}
				
		Query query = daoCategoria.getEntityManager().createQuery(hql.toString());
		
		return (List<Categoria>) query.getResultList();
	}
	
	@Override
	public void saveCategoria(Categoria categoria) {
		daoCategoria.setPersistentClass(Categoria.class);
		daoCategoria.save(categoria);
	}
	
	@Override
	public void updateCategoria(Categoria categoria) {
		daoCategoria.setPersistentClass(Categoria.class);
		daoCategoria.update(categoria);
	}
	
	@Override
	public void deleteCategoria(Long idCategoria) {
		daoCategoria.setPersistentClass(Categoria.class);
		
		daoCategoria.delete(idCategoria);
	}
	
	
	
	

}

