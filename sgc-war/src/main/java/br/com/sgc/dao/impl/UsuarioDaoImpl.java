package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.UsuarioDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Usuario;

@Repository("usuarioDaoImpl")
public class UsuarioDaoImpl implements UsuarioDao{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Dao<Usuario> daoUsuario;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findUsuarios(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT ");
		hql.append(" usu ");
		hql.append(" FROM Usuario usu ");
		hql.append(" where 1 = 1 ");
		
		
		if(usuario != null){ 
			if (usuario.getIdUsuario() != null) {
				hql.append(" AND usu.idUsuario =  " + usuario.getIdUsuario());
			}else{

				if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
					hql.append(" AND usu.nome like  '%" + usuario.getNome().trim() + "%'");
				}
	
				if (usuario.getBairro() != null && !usuario.getBairro().isEmpty()) {
					hql.append(" AND usu.bairro like  '%"
							+ usuario.getBairro().trim() + "%'");
				}
	
				if (usuario.getCep() != null && !usuario.getCep().isEmpty()) {
					hql.append(" AND usu.cep like  '%" + usuario.getCep().trim() + "%'");
				}
	
				if (usuario.getCidade() != null && !usuario.getCidade().isEmpty()) {
					hql.append(" AND usu.cidade like  '%"
							+ usuario.getCidade().trim() + "%'");
				}
	
				if (usuario.getComplemento() != null
						&& !usuario.getComplemento().isEmpty()) {
					hql.append(" AND usu.complemento like  '%"
							+ usuario.getComplemento().trim() + "%'");
				}
	
				if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()) {
					hql.append(" AND usu.cpf like  '%" + usuario.getCpf().trim() + "%'");
				}
	
				if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
					hql.append(" AND usu.email like  '%"
							+ usuario.getEmail().trim() + "%'");
				}
	
				if (usuario.getLogradouro() != null
						&& !usuario.getLogradouro().isEmpty()) {
					hql.append(" AND usu.logradouro like  '%"
							+ usuario.getLogradouro().trim() + "%'");
				}
	
				if (usuario.getNascimento() != null
						&& usuario.getNascimento() != null) {
					hql.append(" AND usu.nascimento like  '%"
							+ usuario.getNascimento() + "%'");
				}
	
				if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
					hql.append(" AND usu.nome like  '%" + usuario.getNome().trim() + "%'");
				}
	
				if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
					hql.append(" AND usu.senha like  '%"
							+ usuario.getSenha().trim() + "%'");
				}
	
				if (usuario.getStatus() != null && !usuario.getStatus().isEmpty()) {
					hql.append(" AND usu.status like  '%"
							+ usuario.getStatus().trim() + "%'");
				}
	
				if (usuario.getTelCel() != null && !usuario.getTelCel().isEmpty()) {
					hql.append(" AND usu.telCel like  '%"
							+ usuario.getTelCel().trim() + "%'");
				}
	
				if (usuario.getTelFixo() != null && !usuario.getTelFixo().isEmpty()) {
					hql.append(" AND usu.telFixo like  '%"
							+ usuario.getTelFixo().trim() + "%'");
				}
	
				if (usuario.getUf() != null && !usuario.getUf().isEmpty()) {
					hql.append(" AND usu.uf like  '%" + usuario.getUf().trim() + "%'");
				}
	
				if (usuario.getUsuario() != null && !usuario.getUsuario().isEmpty()) {
					hql.append(" AND usu.usuario like  '%"
							+ usuario.getUsuario().trim() + "%'");
				}
		
			}
			
			hql.append(" order by usu.idUsuario desc " );
		}
		
		Query query = daoUsuario.getEntityManager().createQuery(hql.toString());
		
		return (List<Usuario>) query.getResultList();
	}
	
	@Override
	public void saveUsuario(Usuario usuario) {
		daoUsuario.setPersistentClass(Usuario.class);
		daoUsuario.save(usuario);
	}
	
	@Override
	public void updateUsuario(Usuario usuario) {
		daoUsuario.setPersistentClass(Usuario.class);
		daoUsuario.update(usuario);
	}
	
	@Override
	public void deleteUsuario(Long idUsuario) {
		daoUsuario.setPersistentClass(Usuario.class);
		daoUsuario.delete(idUsuario);
	}
		
	@Override
	public Usuario findUsuario(Usuario usuario){
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" usu ");
		hql.append(" FROM Usuario usu ");
		hql.append(" where 1 = 1 ");
		
		if(usuario.getIdUsuario() != null){
			hql.append(" AND usu.idUsuario = " + usuario.getIdUsuario());
		}else{
		
			if(usuario.getSenha() != null && !usuario.getSenha().isEmpty()){
				hql.append(" AND usu.senha = '" + usuario.getSenha() +"'");
			}
			
			
			if(usuario.getUsuario() != null && !usuario.getUsuario().isEmpty()){
				hql.append(" AND usu.usuario = '" + usuario.getUsuario().trim() +"'");
			}
		
		}
		Query query = daoUsuario.getEntityManager().createQuery(hql.toString());
		
		@SuppressWarnings("unchecked")
		List<Usuario> list = (List<Usuario>)  query.getResultList();
		
		if(!list.isEmpty()){
			return  list.get(0);
		}else{
			return null;
		}
		
	}
	
	@Override
	public Usuario findUsuarioCpf(String cpf){
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" usu ");
		hql.append(" FROM Usuario usu ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND usu.cpf = :cpf");
		
		Query query = daoUsuario.getEntityManager().createQuery(hql.toString());
		query.setParameter("cpf", cpf);
		
		@SuppressWarnings("unchecked")
		List<Usuario> list = (List<Usuario>)  query.getResultList();
		
		if(!list.isEmpty()){
			return  list.get(0);
		}else{
			return null;
		}
		
	}
	
	@Override
	public Usuario findNomeUsuario(String usuario){
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" usu ");
		hql.append(" FROM Usuario usu ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND usu.usuario = :usuario");
		
		Query query = daoUsuario.getEntityManager().createQuery(hql.toString());
		query.setParameter("usuario", usuario);
		
		@SuppressWarnings("unchecked")
		List<Usuario> list = (List<Usuario>)  query.getResultList();
		
		if(!list.isEmpty()){
			return  list.get(0);
		}else{
			return null;
		}
			
	}
	
}

