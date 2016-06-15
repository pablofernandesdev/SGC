package br.com.sgc.dao.generico.impl;


import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import br.com.sgc.dao.generico.Dao;
import br.com.sgc.dao.generico.to.Paginacao;
import br.com.sgc.dao.generico.util.FilterOperator;
import br.com.sgc.dao.generico.util.LikeField;

/**
 * Implementação do DAO genérico com os métodos necessários para as implementações de DAO do sistema. É
 * necessário herdar esta classe JpaDao<T> para implementar um DAO específico.
 * 
 * 
 * @param <T> Classe da entidade
 * 
 */
@SuppressWarnings("serial")
@Repository("dao")
@Qualifier("dao")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class JpaDao<T> implements Dao<T> {

	private class FindParam implements Comparable<FindParam> {
		private String name;
		private Class<?> type;
		private Object value;
		private boolean key;
		private boolean usaLike;
		private boolean orderByAsc;
		private boolean orderByDesc;
		private int orderN;

		public FindParam(String name, Class<?> type, Object value, boolean key, boolean usaLike,
				boolean orderByAsc, boolean orderByDesc, int orderN) {
			super();
			this.name = name;
			this.type = type;
			this.value = value;
			this.key = key;
			this.usaLike = usaLike;
			this.orderByAsc = orderByAsc;
			this.orderByDesc = orderByDesc;
			this.orderN = orderN;
		}

		@Override
		public int compareTo(FindParam o) {
			return this.orderN - o.orderN;
		}
	}

	private Class<T> persistentClass;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private String databaseRole;

	@Autowired
	private Boolean databaseRoleEnabled;
	private Integer maxResults;

	private Integer startingFrom;

	private String stringHint;

	private boolean booleanHint;

	/**
	 * Recupera o total de registros de uma pesquisa dos objeto da classe da entidade utilizando as
	 * propriedades informadas.
	 * 
	 * @see FilterOperator
	 * @param filters
	 *            os filtros. Pode ser um objeto do tipo da entidade e/ou filtros
	 * @return lista com os objetos encontrados
	 */
	@Override
	public String countFindByFilters(Object... filters) {
		Query query = createQueryFilter(filters, true);
		setHint(query);
		if (getMaxResults() != null) {
			query.setMaxResults(getMaxResults());
		}
		if (getStartingFrom() != null) {
			query.setFirstResult(getStartingFrom());
		}
		return query.getSingleResult().toString();
	}

	/**
	 * Criar query por NamedQuery
	 * 
	 * @param namedQuery
	 * @param paginacao
	 * @param params
	 * @return
	 */
	private Query createNamedQuery(String namedQuery, Paginacao paginacao, Object... params) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setHint(query);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query;
	}

	/**
	 * Criar query por Native Query (SQL)
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private Query createNativeQuery(String sql, Object... params) {
		Query query = null;
		if (getPersistentClass() != null) {
			query = this.entityManager.createNativeQuery(sql, getPersistentClass());
		} else {
			query = this.entityManager.createNativeQuery(sql);
		}
		setHint(query);
		setQueryParams(query, params);
		return query;
	}

	/**
	 * Criar query por HQL query.
	 * 
	 * @param queryStr
	 * @param paginacao
	 * @param params
	 * @return
	 */
	private Query createQuery(String queryStr, Paginacao paginacao, Object... params) {
		Query query = this.entityManager.createQuery(queryStr);
		setHint(query);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query;
	}

	private Query createQueryFilter(Object[] filters, boolean count) {
		StringBuilder qsb = null;
		if (count) {
			qsb = new StringBuilder("select count(*) from ");
		} else {
			qsb = new StringBuilder("select xxx from ");
		}
		StringBuilder orderBy = new StringBuilder();
		qsb.append(getPersistentClass().getSimpleName());
		qsb.append(" as xxx ");
		Map<String, Object> params = new HashMap<String, Object>();
		FilterOperator op = null;
		int filterCount = 0;
		int paramCount = 0;
		if (filters != null) {
			for (int i = 0; i < filters.length;) {
				Object filter = filters[i++];
				if (filterCount > 0) {
					if (filter instanceof FilterOperator) {
						if (filter.equals(FilterOperator.PAR_IN)) {
							qsb.append(" and ( ");
							filter = filters[i++];
						} else if (filter.equals(FilterOperator.PAR_OUT)) {
							qsb.append(" ) ");
							if (i < filters.length) {
								filter = filters[i++];
								if (filter.equals(FilterOperator.PAR_IN)) {
									qsb.append(" and ( ");
									filter = filters[i++];
								} else {
									qsb.append(" and ");
								}
							} else {
								break;
							}
						} else if (filter.equals(FilterOperator.OR)) {
							qsb.append(" or");
							filter = filters[i++];
						} else if (filter.equals(FilterOperator.TO_CHAR)) {
							qsb.append(" and TO_CHAR( ");
							filter = filters[i++];
						} else if (filter.equals(FilterOperator.TO_CHAR_DATE_OUT)) {
							qsb.append(" ,'DD/MM/YYYY') ");
							filter = filters[i++];
						}
					} else {
						qsb.append(" and");
					}
				} else {
					qsb.append(" where");
				}
				if (getPersistentClass().isInstance(filter)) {
					Map<String, FindParam> attrs = new HashMap<String, FindParam>();
					try {
						filterCount += mapJpaObject(filter, attrs, null, false, 0);
					} catch (Exception e) {
						System.err.println("error.query.builder");
						System.err.println(qsb.toString());
						throw new RuntimeException("error.query.builder");
					}
					qsb.append(findByAttrs(attrs, params));

				} else {
					if (filter instanceof FilterOperator) {
						op = (FilterOperator) filter;
					} else {
						qsb.append(" xxx.");
						qsb.append(filter);
						Object oop = filters[i++];
						if (oop instanceof FilterOperator) {
							op = (FilterOperator) oop;
						} else {
							System.err.println("error.query.builder");
							System.err.println(oop);
							System.err.println(qsb.toString());
							throw new RuntimeException("error.query.builder");
						}
					}
					qsb.append(" ");
					qsb.append(op.getStringValue());
					if (op != null) {
						if (op.isUseParenthesis()) {
							qsb.append("(");
						}
						int np = op.getNumParams();
						while (np-- > 0) {
							params.put("param_" + (++paramCount), filters[i++]);
							qsb.append(" :param_" + paramCount);
							if (np > 0) {
								qsb.append(" and ");
							}
						}
						if (op.isUseParenthesis()) {
							qsb.append(")");
						}
						filterCount++;
					}
				}
			}
		}
		if (getPersistentClass() != null) {
			Map<String, FindParam> attrs = new HashMap<String, FindParam>();
			try {
				filterCount += mapJpaObjectOrderBy(getPersistentClass(), attrs, null);
			} catch (Exception e) {
				System.err.println("error.query.builder");
				System.err.println(qsb.toString());
				e.printStackTrace();
				throw new RuntimeException("error.query.builder");
			}
			findByAttrsOrderBy(attrs, orderBy);
		}
		qsb.append(orderBy);
		Query q = this.entityManager.createQuery(qsb.toString());
		setQueryParams(q, params);
		return q;
	}

	private List<?> createQueryHql(String queryStr, Paginacao paginacao, Map<String, Object> params) {
		Query query = this.entityManager.createQuery(queryStr);
		setHint(query);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	/**
	 * Apagar um objeto do banco.
	 * 
	 * @param obj
	 *            o objeto
	 */
	@Override
	public void delete(Serializable id) {
		this.setRole();
		this.entityManager.remove(findById(id));
	}

	/**
	 * Para casos especiais visando o desempenho da aplicação.
	 * 
	 * @param namedQuery
	 * @param params
	 */
	@Override
	public void deleteWithNamedQuery(String namedQuery, Object... params) {
		this.setRole();
		createNamedQuery(namedQuery, null, params).executeUpdate();
	}

	/**
	 * Para casos especiais visando o desempenho da aplicação.
	 * 
	 * @param sql
	 * @param params
	 */
	@Override
	public void deleteWithNativeQuery(String sql, Object... params) {
		this.setRole();
		createNativeQuery(sql, params).executeUpdate();
	}

	/**
	 * Pesquisar por uma hql query, os parâmetros são opcionais.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@Override
	public List<?> find(String queryStr, Object... params) {
		return find(queryStr, null, params);
	}

	/**
	 * Pesquisar por uma hql query, os parâmetros e a paginação são opcionais.
	 * 
	 * @param queryStr
	 * @param paginacao
	 * @param params
	 * @return
	 */
	@Override
	public List<?> find(String queryStr, Paginacao paginacao, Object... params) {
		return createQuery(queryStr, paginacao, params).getResultList();
	}

	/**
	 * Recuperar todos os registros de uma entidade.
	 * 
	 * @return
	 */
	@Override
	public List<T> findAll() {
		return findAll(null);
	}

	/**
	 * Recuperar todos os registros de uma entidade com paginação.
	 * 
	 * @param paginacao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Paginacao paginacao) {
		// Query query = this.entityManager.createQuery(" FROM " + getPersistentClass().getSimpleName());
		Query query = createQueryFilter(null, false);
		setHint(query);
		paginar(paginacao, query);
		return query.getResultList();
	}

	/**
	 * Constroi a query baseado nos parametros da consulta
	 * 
	 * @param attrs
	 *            parametros da consulta
	 * @return lista com objetos encontrados
	 */
	private String findByAttrs(Map<String, FindParam> attrs, Map<String, Object> params) {
		StringBuilder jpql = new StringBuilder();
		if ((attrs != null) && (attrs.size() > 0)) {
			Set<Entry<String, FindParam>> entries = attrs.entrySet();

			/*
			 * Verificacao para o caso de o Map conter apenas valores para OrdeBy, sem valores para
			 * parametros.
			 */
			int i = 0;
			for (Entry<String, FindParam> entry : entries) {
				if (!isEmpty(entry.getValue().value, entry.getValue().type)) {
					if (i++ > 0) {
						jpql.append(" and");
					}
					if (entry.getValue().usaLike && (entry.getValue().type == String.class)
							&& !entry.getValue().key) {
						// jpql.append(REMOVE_ACENTOS_1);
						jpql.append(" upper(xxx.");
						jpql.append(entry.getKey());
						jpql.append(")");
						// jpql.append(REMOVE_ACENTOS_2);
						jpql.append(" like ");
						// jpql.append(REMOVE_ACENTOS_1);
						jpql.append("upper(");
						jpql.append(":");
						jpql.append(key2ParamName(entry.getKey()));
						jpql.append(")");
						// jpql.append(REMOVE_ACENTOS_2);
						entry.getValue().value = "%" + entry.getValue().value + "%";
					} else {
						jpql.append(" xxx.");
						jpql.append(entry.getKey());
						jpql.append(" = ");
						jpql.append(" :");
						jpql.append(key2ParamName(entry.getKey()));
					}
					params.put(key2ParamName(entry.getKey()), entry.getValue().value);
				}
			}
		}
		return jpql.toString();
	}

	private String findByAttrsOrderBy(Map<String, FindParam> attrs, StringBuilder orderBy) {
		StringBuilder jpql = new StringBuilder();
		if ((attrs != null) && (attrs.size() > 0)) {
			Set<Entry<String, FindParam>> entries = attrs.entrySet();

			/*
			 * Verificacao para o caso de o Map conter apenas valores para OrdeBy, sem valores para
			 * parametros.
			 */
			List<FindParam> orderBys = new ArrayList<FindParam>();
			for (Entry<String, FindParam> entry : entries) {
				if (entry.getValue().orderByAsc || entry.getValue().orderByDesc) {
					orderBys.add(entry.getValue());
				}
			}
			Collections.sort(orderBys);
			for (FindParam param : orderBys) {
				if (orderBy.length() == 0) {
					orderBy.append(" order by ");
					orderBy.append(" xxx.");
					orderBy.append(param.name);
				} else {
					orderBy.append(", xxx.");
					orderBy.append(param.name);
				}
				if (param.orderByAsc) {
					orderBy.append(" ASC ");
				} else if (param.orderByDesc) {
					orderBy.append(" DESC ");
				}
			}
		}
		return jpql.toString();
	}

	/**
	 * 
	 * Recupera os registros de uma pesquisa dos objeto da classe da entidade utilizando as propriedades
	 * informadas.
	 * 
	 * @see FilterOperator
	 * @param filters
	 *            os filtros. Pode ser um objeto do tipo da entidade e/ou filtros
	 * @return lista com os objetos encontrados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByFilters(Object... filters) {
		Query query = createQueryFilter(filters, false);
		setHint(query);
		if (getMaxResults() != null) {
			query.setMaxResults(getMaxResults());
		}
		if (getStartingFrom() != null) {
			query.setFirstResult(getStartingFrom());
		}
		return query.getResultList();
	}

	/**
	 * Recuperar um objeto pelo seu identificador.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public T findById(Serializable id) {
		return this.entityManager.find(getPersistentClass(), id);
	}

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros nomeados.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedParams(String queryname, Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(queryname);
		setHint(query);
		setQueryParams(query, params);
		return query.getResultList();
	}

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros nomeados com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedParams(String queryname, Paginacao paginacao, Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(queryname);
		setHint(query);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	/**
	 * Pesquisar por uma hql query, os parâmetros são nomeados e opcionais.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@Override
	public List<?> findByNamedParamsHql(String queryStr, Map<String, Object> params) {
		return createQueryHql(queryStr, null, params);
	}

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros sem nome.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	@Override
	public List<T> findByNamedQuery(String namedQuery, Object... params) {
		return findByNamedQuery(namedQuery, null, params);
	}

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros sem nome com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String namedQuery, Paginacao paginacao, Object... params) {
		Query query = createNamedQuery(namedQuery, paginacao, params);
		return query.getResultList();
	}

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros sem nome.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados
	 */
	@Override
	public List<T> findByNativeQuery(String sql, Object... params) {
		return findByNativeQuery(sql, null, params);
	}

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros sem nome com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNativeQuery(String sql, Paginacao paginacao, Object... params) {
		Query query = createNativeQuery(sql, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros com nome sem opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	@Override
	public List<T> findByNativeQueryNamedParams(String sql, Map<String, Object> params) {
		return findByNativeQueryNamedParams(sql, null, params);
	}

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros com nome e com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNativeQueryNamedParams(String sql, Paginacao paginacao, Map<String, Object> params) {
		Query query = this.entityManager.createNativeQuery(sql);
		setHint(query);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	/**
	 * Pesquisar utilizando uma native query SQL contendo parâmetros sem nome e retornando único objeto.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos sem tipagem encontrados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findByNativeQuerySingleResult(String queryStr, Object... params) {
		try {
			return (T) createNativeQuery(queryStr, params).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Pesquisar utilizando uma native query SQL contendo parâmetros sem nome e retornando objetos.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos sem tipagem encontrados
	 */
	@Override
	public List<?> findObjectsByNativeQuery(String sql, Object... params) {
		return findByNativeQuery(sql, null, params);
	}

	/**
	 * Pesquisar por uma hql query, os parâmetros e são opcionais e retorna um resultado único.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@Override
	public Object findSingleResult(String queryStr, Object... params) {
		try {
			return createQuery(queryStr, null, params).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Pesquisar por uma named query, os parâmetros são opcionais e retorna um resultado único.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@Override
	public Object findSingleResultByNamedQuery(String namedQuery, Object... params) {
		try {
			return createNamedQuery(namedQuery, null, params).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@SuppressWarnings("hiding")
	private <T extends Annotation> T getFieldAnnotation(Field field, Class<T> class1) {
		try {
			T ann = field.getAnnotation(class1);
			if (ann == null) {
				Class<?> cls = field.getDeclaringClass();
				String name = (field.getType() == boolean.class ? "is" : "get")
						+ field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				try {
					Method method = cls.getDeclaredMethod(name);
					ann = method.getAnnotation(class1);
				} catch (Exception e) {
				}
			}
			return ann;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the maxResults
	 */
	public Integer getMaxResults() {
		return this.maxResults;
	}

	public final Class<T> getPersistentClass() {
		if (this.persistentClass == null) {
			throw new RuntimeException("É necessário invocar o método setPersistentClass(Class<T> clazz)");
		}
		return this.persistentClass;
	}

	/**
	 * @return the startingFrom
	 */
	public Integer getStartingFrom() {
		return this.startingFrom;
	}

	private boolean isEmpty(Object value, Class<?> type) {
		if (value instanceof String) {
			return ((String) value).isEmpty();
		} else if (value instanceof Long) {
			return value.equals(0L);
		} else if (value instanceof Double) {
			return value.equals(0d);
		} else if (value instanceof Float) {
			return value.equals(0f);
		} else if (value instanceof Number) {
			return value.equals(0);
		}
		return value == null;
	}

	private String key2ParamName(String key) {
		return key.replaceAll("[.]", "_");
	}

	private int mapJpaObject(Object obj, Map<String, FindParam> map, String alias, boolean idObj, int orderN)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			IntrospectionException {
		int filterCount = 0;
		Field[] fields = obj.getClass().getDeclaredFields();
		String aliasprefix = alias != null ? alias + "." : "";
		for (Field field : fields) {
			Transient transien = field.getAnnotation(Transient.class);
			if (((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC)
					&& ((field.getModifiers() & Modifier.TRANSIENT) != Modifier.TRANSIENT)
					&& (transien == null)) {
				Object value = PropertyUtils.getSimpleProperty(obj, field.getName());
				OrderBy orderBy = getFieldAnnotation(field, OrderBy.class);
				/*
				 * Desvia de listas e valores nulos
				 */
				if ((value != null) && !(value instanceof Collection) && !isEmpty(value, field.getType())) {
					ManyToOne manyToOne = getFieldAnnotation(field, ManyToOne.class);
					OneToOne oneToOne = getFieldAnnotation(field, OneToOne.class);
					EmbeddedId embId = getFieldAnnotation(field, EmbeddedId.class);
					Id id = getFieldAnnotation(field, Id.class);
					LikeField usaContains = getFieldAnnotation(field, LikeField.class);
					String keyName = aliasprefix + field.getName();
					// se algum objeto relacionado ou chave composta ent�o aprofunda nele
					if ((manyToOne != null) || (embId != null) || (oneToOne != null)) {

						filterCount += mapJpaObject(value, map, aliasprefix + field.getName(), embId != null,
								orderN + 1);

					} else {
						// caso seja um campo comum adiciona-o a query
						map.put(keyName, new FindParam(keyName, field.getType(), value,
								idObj || (id != null), usaContains != null, orderBy != null ? orderBy.value()
										.equals("ASC") : false, orderBy != null ? orderBy.value().equals(
										"DESC") : false, orderN));
						filterCount++;
					}
				} else if ((orderBy != null) && !(value instanceof Collection)) {
					String keyName = aliasprefix + field.getName();
					map.put(keyName, new FindParam(keyName, null, null, false, false,
							orderBy != null ? !orderBy.value().equals("DESC") : false,
							orderBy != null ? orderBy.value().equals("DESC") : false, orderN));
				}
			}
		}
		return filterCount;
	}

	private int mapJpaObjectOrderBy(Class<?> clss, Map<String, FindParam> map, String alias)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			IntrospectionException {
		int filterCount = 0;
		Field[] fields = clss.getDeclaredFields();
		String aliasprefix = alias != null ? alias + "." : "";
		for (Field field : fields) {
			Transient transien;
			transien = getFieldAnnotation(field, Transient.class);
			if (((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC)
					&& ((field.getModifiers() & Modifier.TRANSIENT) != Modifier.TRANSIENT)
					&& (transien == null)) {
				OrderBy orderBy = getFieldAnnotation(field, OrderBy.class);
				/*
				 * Desvia de listas e valores nulos
				 */
				EmbeddedId embId = getFieldAnnotation(field, EmbeddedId.class);
				if ((orderBy != null) && !(Collection.class.isAssignableFrom(field.getType()))) {
					String keyName = aliasprefix + field.getName();
					map.put(keyName, new FindParam(keyName, null, null, false, false,
							orderBy != null ? !orderBy.value().equals("DESC") : false,
							orderBy != null ? orderBy.value().equals("DESC") : false, 0));
				} else if (embId != null) {
					mapJpaObjectOrderBy(field.getType(), map, aliasprefix + field.getName());
				}
			}
		}
		return filterCount;
	}

	/**
	 * Atribuir paginação a query.
	 * 
	 * @param paginacao
	 * @param query
	 */
	private void paginar(Paginacao paginacao, Query query) {
		if (paginacao != null) {
			if (paginacao.getPosicao() != null) {
				query.setFirstResult(paginacao.getPosicao());
			}
			if (paginacao.getLimite() != null) {
				query.setMaxResults(paginacao.getLimite());
			}
		}
	}

	public void refresh(T entity) {
		this.entityManager.refresh(entity);
	}

	@Override
	public void save(T obj) {
		this.setRole();
		this.entityManager.persist(obj);
	}

	/**
	 * Definir qual tipo de Hint, como exemplo, cache 2LC do hibernate.
	 * 
	 * @param query
	 */
	private void setHint(Query query) {
		if (this.booleanHint && (this.stringHint != null) && !this.stringHint.trim().isEmpty()) {
			query.setHint(this.stringHint, this.booleanHint);
		}
	}

	/**
	 * Definir qual tipo de Hint, como exemplo, cache 2LC do hibernate.
	 * 
	 * @param query
	 */
	public void setHint(String stringHint, boolean booleanHint) {
		this.stringHint = stringHint;
		this.booleanHint = booleanHint;
	}

	/**
	 * @param maxResults
	 *            the maxResults to set
	 */
	@Override
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public final void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * Atribuir os parâmetros nomeados da query.
	 * 
	 * @param query
	 * @param params
	 */
	private void setQueryParams(Query query, Map<String, Object> params) {
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Atribuir os parâmetros da query.
	 * 
	 * @param query
	 * @param params
	 */
	private void setQueryParams(Query query, Object... params) {
		if ((params != null) && (params.length > 0)) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
	}

	/**
	 * Atribuir a Role para as operações solicitadas pelo usuário.
	 * 
	 */
	private void setRole() {
		if (this.databaseRoleEnabled && !this.databaseRole.isEmpty()) {
			this.entityManager.createNativeQuery(this.databaseRole).executeUpdate();
		}
	}

	/**
	 * @param startingFrom
	 *            the startingFrom to set
	 */
	@Override
	public void setStartingFrom(Integer startingFrom) {
		this.startingFrom = startingFrom;
	}

	/**
	 * Se o objeto existir, atualiza o objeto no banco. Se não existir cria-se o objeto no banco.
	 * 
	 * @param obj
	 *            o objeto
	 */
	@Override
	public void update(T obj) {
		this.setRole();
		this.entityManager.merge(obj);
	}

	/**
	 * Atualizar registro utilizando uma sql nativa.
	 * 
	 * @param sql
	 * @param params
	 */
	@Override
	public void updateWithNativeQuery(String sql, Object... params) {
		this.setRole();
		createNativeQuery(sql, params).executeUpdate();
	}

}
