package br.com.sgc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.FornecedorDao;
import br.com.sgc.dao.generico.Dao;
import br.com.sgc.domain.Fornecedor;

@Repository("fornecedorDaoImpl")
public class FornecedorDaoImpl implements FornecedorDao {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Dao<Fornecedor> daoFornecedor;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Fornecedor> findFornecedores(Fornecedor fornecedor) {

		StringBuilder hql = new StringBuilder();

		hql.append(" SELECT ");
		hql.append(" for ");
		hql.append(" FROM Fornecedor for ");
		hql.append(" where 1 = 1 ");

		if (fornecedor != null) {
			if (fornecedor.getIdFornecedor() != null) {
				hql.append(" AND for.idFornecedor =  "
						+ fornecedor.getIdFornecedor());
			}

			if (fornecedor.getBairro() != null
					&& !fornecedor.getBairro().isEmpty()) {
				hql.append(" AND for.bairro like  '%"
						+ fornecedor.getBairro().trim() + "%'");
			}

			if (fornecedor.getCidade() != null
					&& !fornecedor.getCidade().isEmpty()) {
				hql.append(" AND for.cidade like  '%"
						+ fornecedor.getCidade().trim() + "%'");
			}

			if (fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()) {
				hql.append(" AND for.cnpj like  '%"
						+ fornecedor.getCnpj().trim() + "%'");
			}

			if (fornecedor.getComplemento() != null
					&& !fornecedor.getComplemento().isEmpty()) {
				hql.append(" AND for.complemento like  '%"
						+ fornecedor.getComplemento().trim() + "%'");
			}

			if (fornecedor.getEmail() != null
					&& !fornecedor.getEmail().isEmpty()) {
				hql.append(" AND for.email like  '%"
						+ fornecedor.getEmail().trim() + "%'");
			}

			if (fornecedor.getLogradouro() != null
					&& !fornecedor.getLogradouro().isEmpty()) {
				hql.append(" AND for.logradouro like  '%"
						+ fornecedor.getLogradouro().trim() + "%'");
			}

			if (fornecedor.getNomeFantasia() != null
					&& !fornecedor.getNomeFantasia().isEmpty()) {
				hql.append(" AND for.nomeFantasia like  '%"
						+ fornecedor.getNomeFantasia().trim() + "%'");
			}

			if (fornecedor.getRazaoSocial() != null
					&& !fornecedor.getRazaoSocial().isEmpty()) {
				hql.append(" AND for.razaoSocial like  '%"
						+ fornecedor.getRazaoSocial().trim() + "%'");
			}

			if (fornecedor.getTelCel() != null
					&& !fornecedor.getTelCel().isEmpty()) {
				hql.append(" AND for.telCel like  '%"
						+ fornecedor.getBairro().trim() + "%'");
			}

			if (fornecedor.getTelFixo() != null
					&& !fornecedor.getTelFixo().isEmpty()) {
				hql.append(" AND for.telFixo like  '%"
						+ fornecedor.getTelFixo().trim() + "%'");
			}

			if (fornecedor.getUf() != null && !fornecedor.getUf().isEmpty()) {
				hql.append(" AND for.uf like  '%" + fornecedor.getUf().trim()
						+ "%'");
			}
			
			hql.append(" order by for.idFornecedor desc " );
		}

		Query query = daoFornecedor.getEntityManager().createQuery(
				hql.toString());

		return (List<Fornecedor>) query.getResultList();
	}

	@Override
	public void saveFornecedor(Fornecedor fornecedor) {
		daoFornecedor.setPersistentClass(Fornecedor.class);
		daoFornecedor.save(fornecedor);
	}

	@Override
	public void updateFornecedor(Fornecedor fornecedor) {
		daoFornecedor.setPersistentClass(Fornecedor.class);
		daoFornecedor.update(fornecedor);
	}

	@Override
	public void deleteFornecedor(Long idFornecedor) {
		daoFornecedor.setPersistentClass(Fornecedor.class);
		daoFornecedor.delete(idFornecedor);
	}

	@Override
	public Fornecedor findFornecedorCnpj(String cnpj) {

		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" for ");
		hql.append(" FROM Fornecedor for ");
		hql.append(" where 1 = 1 ");
		hql.append(" AND for.cnpj = :cnpj");

		Query query = daoFornecedor.getEntityManager().createQuery(
				hql.toString());
		query.setParameter("cnpj", cnpj);

		@SuppressWarnings("unchecked")
		List<Fornecedor> list = (List<Fornecedor>) query.getResultList();

		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}

	}

}
