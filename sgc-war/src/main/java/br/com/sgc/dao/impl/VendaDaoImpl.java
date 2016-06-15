package br.com.sgc.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.VendaDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Produto;
import br.com.sgc.domain.SaidaProduto;
import br.com.sgc.domain.Venda;

@Repository("vendaDaoImpl")
public class VendaDaoImpl implements VendaDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Venda> daoVenda;
	
	@Autowired
	private Dao<SaidaProduto> daoSaidaProduto;
	
	@Autowired
	private Dao<Produto> daoProduto;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> findVendas(Venda venda) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ven ");
		hql.append(" FROM Venda ven ");
		hql.append(" where 1 = 1 ");
		
		if(venda != null){
			if(venda.getIdVenda() != null){
				hql.append(" AND ven.idVenda =  " + venda.getIdVenda());
			}else{
			
			if(venda.getTotal() != null){
				hql.append(" AND ven.total =  :total " );
			}
			
			if(venda.getDataVenda() != null && venda.getDataVenda() != null){
				hql.append(" AND  ven.dataVenda  =  :dataVenda "  );
			}
			
			if(venda.getCliente().getNome()!= null && !venda.getCliente().getNome().isEmpty()){
				hql.append(" AND ven.cliente.nome like  '%" + venda.getCliente().getNome() +"%'");
			}
			
			if(venda.getUsuario().getNome()!= null && !venda.getUsuario().getNome().isEmpty()){
				hql.append(" AND ven.usuario.nome like  '%" + venda.getUsuario().getNome() +"%'");
			}
		   }
			
			hql.append(" order by ven.idVenda desc " );
		}
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		if(venda.getIdVenda() != null){
			query.setParameter("idVenda", venda.getIdVenda());
		}
			
		if(venda.getTotal() != null){
			query.setParameter("total", venda.getTotal());
		}
		
		if(venda.getDataVenda() != null && venda.getDataVenda() != null){
			query.setParameter("dataVenda", venda.getDataVenda());
		}
			
		return (List<Venda>) query.getResultList();
	}
	
	@Override
	public void saveVenda(Venda venda, List<SaidaProduto> listaSaidaProduto) {
		daoVenda.setPersistentClass(Venda.class);
		daoSaidaProduto.setPersistentClass(SaidaProduto.class);
		daoProduto.setPersistentClass(Produto.class);
		daoVenda.save(venda);
		
		for(SaidaProduto saidaProduto : listaSaidaProduto){
			saidaProduto.setVenda(venda);
			saidaProduto.setData(new Date());
			saidaProduto.setPrecoVendaSaida(saidaProduto.getProduto().getPrecoVenda());
			daoSaidaProduto.save(saidaProduto);
			
			Produto produto =  daoProduto.findById(saidaProduto.getProduto().getIdProduto());
			produto.setQuantidade(produto.getQuantidade() - saidaProduto.getQuantidadeSaida());
			daoProduto.save(produto);
		}
			
	}
	
	@Override
	public void updateVenda(Venda venda) {
		daoVenda.setPersistentClass(Venda.class);
		daoVenda.update(venda);
	}
	
	@Override
	public void deleteVenda(Long idVenda) {
		daoVenda.setPersistentClass(Venda.class);
		daoVenda.delete(idVenda);
	}
	
	//verifica se há algum registro entre as tabelas
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> findVendaByCliente(Long idCliente){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ven ");
		hql.append(" FROM Venda ven ");
		hql.append(" inner join ven.cliente cli ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND cli.idCliente =  " + idCliente);
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		
		return (List<Venda>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> findVendaByEmpresa(Long idEmpresa){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ven ");
		hql.append(" FROM Venda ven ");
		hql.append(" inner join ven.empresa emp ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND emp.idEmpresa =  " + idEmpresa);
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		
		return (List<Venda>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> findVendaByUsuario(Long idUsuario){
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ven ");
		hql.append(" FROM Venda ven ");
		hql.append(" inner join ven.usuario usu ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND usu.idUsuario =  " + idUsuario);
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		
		return (List<Venda>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SaidaProduto> findSaidaProdutoByIdVenda(Long idVenda) {
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" saidaProduto ");
		hql.append(" FROM SaidaProduto saidaProduto ");
		hql.append(" inner join fetch saidaProduto.produto prod ");
		hql.append(" inner join fetch saidaProduto.venda ven ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND ven.idVenda =  " + idVenda);
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		
		return (List<SaidaProduto>) query.getResultList();
	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> findValorTotalVendaByPeriodoUsuario(Date dataInicio, Date dataFim , Long codUsuario) {
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" ven ");
		hql.append(" FROM Venda ven ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND ven.dataVenda between :dataInicio and :dataFim  " );
		
		if(codUsuario != null){
			hql.append(" AND ven.idUsuario = :codUsuario " );
		}
				
		Query query = daoVenda.getEntityManager().createQuery(hql.toString());
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		if(codUsuario != null){
			query.setParameter("codUsuario", codUsuario);
		}
		
		return (List<Venda>) query.getResultList();
	
	}
	
	

}


