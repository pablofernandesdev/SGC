package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.EmpresaDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Empresa;


@Repository("empresaDaoImpl")
public class EmpresaDaoImpl implements EmpresaDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Empresa> daoEmpresa;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> findEmpresas(Empresa empresa) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" for ");
		hql.append(" FROM Empresa for ");
		hql.append(" where 1 = 1 ");
		
		if(empresa != null){ 
		if(empresa.getIdEmpresa() != null){
			hql.append(" AND for.idEmpresa =  " + empresa.getIdEmpresa());
		}
		
		if(empresa.getBairro() != null && !empresa.getBairro().isEmpty()){
			hql.append(" AND for.bairro like  '%" + empresa.getBairro().trim() +"%'");
		}
		
		if(empresa.getCidade() != null && !empresa.getCidade().isEmpty()){
			hql.append(" AND for.cidade like  '%" + empresa.getCidade().trim() +"%'");
		}
		
		if(empresa.getCnpj() != null && !empresa.getCnpj().isEmpty()){
			hql.append(" AND for.cnpj like  '%" + empresa.getCnpj().trim() +"%'");
		}
		
		if(empresa.getComplemento() != null && !empresa.getComplemento().isEmpty()){
			hql.append(" AND for.complemento like  '%" + empresa.getComplemento().trim() +"%'");
		}
		
		if(empresa.getEmail() != null && !empresa.getEmail().isEmpty()){
			hql.append(" AND for.email like  '%" + empresa.getEmail().trim() +"%'");
		}
		
		if(empresa.getLogradouro() != null && !empresa.getLogradouro().isEmpty()){
			hql.append(" AND for.logradouro like  '%" + empresa.getLogradouro().trim() +"%'");
		}
		
		if(empresa.getNomeFantasia() != null && !empresa.getNomeFantasia().isEmpty()){
			hql.append(" AND for.nomeFantasia like  '%" + empresa.getNomeFantasia().trim() +"%'");
		}
		
		if(empresa.getRazaoSocial() != null && !empresa.getRazaoSocial().isEmpty()){
			hql.append(" AND for.razaoSocial like  '%" + empresa.getRazaoSocial().trim() +"%'");
		}
		
		if(empresa.getTelCel() != null && !empresa.getTelCel().isEmpty()){
			hql.append(" AND for.telCel like  '%" + empresa.getBairro().trim() +"%'");
		}
		
		if(empresa.getTelFixo() != null && !empresa.getTelFixo().isEmpty()){
			hql.append(" AND for.telFixo like  '%" + empresa.getTelFixo().trim() +"%'");
		}
		
		if(empresa.getUf() != null && !empresa.getUf().isEmpty()){
			hql.append(" AND for.uf like  '%" + empresa.getUf().trim() +"%'");
		}
		}
		
		
		Query query = daoEmpresa.getEntityManager().createQuery(hql.toString());
		
		return (List<Empresa>) query.getResultList();
	}
	
	@Override
	public void saveEmpresa(Empresa empresa) {
		daoEmpresa.setPersistentClass(Empresa.class);
		daoEmpresa.save(empresa);
	}
	
	@Override
	public void updateEmpresa(Empresa empresa) {
		daoEmpresa.setPersistentClass(Empresa.class);
		daoEmpresa.update(empresa);
	}
	
	@Override
	public void deleteEmpresa(Long idEmpresa) {
		daoEmpresa.setPersistentClass(Empresa.class);
		daoEmpresa.delete(idEmpresa);
	}
	
	@Override
	public Empresa findEmpresaCnpj(String cnpj){
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" for ");
		hql.append(" FROM Empresa for ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND for.cnpj = :cnpj");
		
		Query query = daoEmpresa.getEntityManager().createQuery(hql.toString());
		query.setParameter("cnpj", cnpj);
		
		@SuppressWarnings("unchecked")
		List<Empresa> list = (List<Empresa>)  query.getResultList();
		
		if(!list.isEmpty()){
			return  list.get(0);
		}else{
			return null;
		}
		
		
	}
	

}

