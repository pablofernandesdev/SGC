package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.ClienteDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Cliente;

@Repository("clienteDaoImpl")
public class ClienteDaoImpl implements ClienteDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Cliente> daoCliente;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findClientes(Cliente cliente) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" cli ");
		hql.append(" FROM Cliente cli ");
		hql.append(" where 1 = 1 ");
		
		if(cliente.getIdCliente() != null){
			hql.append(" AND cli.idCliente =  " + cliente.getIdCliente());
		}
		
		if(cliente.getNome() != null && !cliente.getNome().isEmpty()){
			hql.append(" AND cli.nome like  '%" + cliente.getNome().trim() +"%'");
		}
		
		if(cliente.getBairro() != null && !cliente.getBairro().isEmpty()){
			hql.append(" AND cli.bairro like  '%" + cliente.getBairro().trim() +"%'");
		}
		
		if(cliente.getCep() != null && !cliente.getCep().isEmpty()){
			hql.append(" AND cli.cep like  '%" + cliente.getCep().trim() +"%'");
		}
		
		if(cliente.getCidade() != null && !cliente.getCidade().isEmpty()){
			hql.append(" AND cli.cidade like  '%" + cliente.getCidade().trim() +"%'");
		}
		
		if(cliente.getComplemento() != null && !cliente.getComplemento().isEmpty()){
			hql.append(" AND cli.complemento like  '%" + cliente.getComplemento().trim() +"%'");
		}
		
		if(cliente.getCpf() != null && !cliente.getCpf().isEmpty()){
			hql.append(" AND cli.cpf like  '%" + cliente.getCpf().trim() +"%'");
		}
		
		if(cliente.getEmail() != null && !cliente.getEmail().isEmpty()){
			hql.append(" AND cli.email like  '%" + cliente.getEmail().trim() +"%'");
		}
		
		if(cliente.getLogradouro() != null && !cliente.getLogradouro().isEmpty()){
			hql.append(" AND cli.logradouro like  '%" + cliente.getLogradouro().trim() +"%'");
		}
		
		if(cliente.getNascimento() != null && cliente.getNascimento() != null){
			hql.append(" AND cli.nascimento like  '%" + cliente.getNascimento() +"%'");
		}
		
		if(cliente.getTelCel() != null && !cliente.getTelCel().isEmpty()){
			hql.append(" AND cli.telCel like  '%" + cliente.getTelCel().trim() +"%'");
		}
		
		if(cliente.getTelFixo() != null && !cliente.getTelFixo().isEmpty()){
			hql.append(" AND cli.telFixo like  '%" + cliente.getTelFixo().trim() +"%'");
		}
		
		if(cliente.getUf() != null && !cliente.getUf().isEmpty()){
			hql.append(" AND cli.uf like  '%" + cliente.getUf().trim() +"%'");
		}
		
		hql.append(" order by cli.idCliente desc " );
		
		Query query = daoCliente.getEntityManager().createQuery(hql.toString());
		
		return (List<Cliente>) query.getResultList();
	}
	
	@Override
	public void saveCliente(Cliente cliente) {
		daoCliente.setPersistentClass(Cliente.class);
		daoCliente.save(cliente);
	}
	
	@Override
	public void updateCliente(Cliente cliente) {
		daoCliente.setPersistentClass(Cliente.class);
		daoCliente.update(cliente);
	}
	
	@Override
	public void deleteCliente(Long idCliente) {
		daoCliente.setPersistentClass(Cliente.class);
		daoCliente.delete(idCliente);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Cliente findClienteCpf(String cpf){
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" cli ");
		hql.append(" FROM Cliente cli ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND cli.cpf = :cpf");
		
		Query query = daoCliente.getEntityManager().createQuery(hql.toString());
		query.setParameter("cpf", cpf);
		
		List<Cliente> list = (List<Cliente>)  query.getResultList();
		
		if(!list.isEmpty()){
			return  list.get(0);
		}else{
			return null;
		}
		
		
	}
	
	
}

