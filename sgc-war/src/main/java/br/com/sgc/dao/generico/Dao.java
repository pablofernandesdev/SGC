package br.com.sgc.dao.generico;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.sgc.dao.generico.to.Paginacao;
import br.com.sgc.dao.generico.util.FilterOperator;

/**
 * DAO genérico com os métodos necessários para as implementações dos serviços do sistema
 * 
 * @param <T>
 *            Classe da entidade
 */
public interface Dao<T> extends Serializable {

	/**
	 * Recupera o total de registros de uma pesquisa dos objeto da classe da entidade utilizando as
	 * propriedades informadas.
	 * 
	 * @see FilterOperator
	 * @param filters os filtros. Pode ser um objeto do tipo da entidade e/ou filtros
	 * @return lista com os objetos encontrados
	 */
	public String countFindByFilters(Object... filters);

	/**
	 * Apagar um objeto do banco.
	 * 
	 * @param objeto
	 */
	void delete(Serializable id);

	/**
	 * Para casos especiais visando o desempenho da aplicação
	 * 
	 * @param namedQuery
	 * @param params
	 */
	void deleteWithNamedQuery(String namedQuery, Object... params);

	/**
	 * Para casos especiais visando o desempenho da aplicação
	 * 
	 * @param sql
	 * @param params
	 */
	void deleteWithNativeQuery(String sql, Object... params);

	/**
	 * Pesquisar por uma hql query, os parâmetros são opcionais.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	List<?> find(String queryStr, Object... params);

	/**
	 * Pesquisar por uma hql query, os parâmetros e a paginação são opcionais.
	 * 
	 * @param queryStr
	 * @param paginacao
	 * @param params
	 * @return
	 */
	List<?> find(String queryStr, Paginacao paginacao, Object... params);

	/**
	 * Recuperar todos os registros de uma entidade.
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * Recuperar todos os registros de uma entidade com paginação.
	 * 
	 * @param paginacao
	 * @return
	 */
	List<T> findAll(Paginacao paginacao);

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
	List<T> findByFilters(Object... filters);

	/**
	 * Recuperar um objeto pelo seu identificador.
	 * 
	 * @param id
	 * @return
	 */
	T findById(Serializable id);

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros nomeados.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	List<T> findByNamedParams(String queryname, Map<String, Object> params);

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros nomeados com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	List<T> findByNamedParams(String queryname, Paginacao paginacao, Map<String, Object> params);

	/**
	 * Pesquisar por uma hql query, os parâmetros são nomeados e opcionais.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	List<?> findByNamedParamsHql(String queryStr, Map<String, Object> params);

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros sem nome.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	List<T> findByNamedQuery(String namedQuery, Object... params);

	/**
	 * Pesquisar utilizando uma named query contendo parâmetros sem nome com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryname
	 *            nome da query
	 * @return lista com os objetos encontrados
	 */
	List<T> findByNamedQuery(String namedQuery, Paginacao paginacao, Object... params);

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros sem nome.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados
	 */
	List<T> findByNativeQuery(String sql, Object... params);

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros sem nome com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	List<T> findByNativeQuery(String sql, Paginacao paginacao, Object... params);

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros com nome sem opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	List<T> findByNativeQueryNamedParams(String sql, Map<String, Object> params);

	/**
	 * Pesquisar utilizando uma query SQL contendo parâmetros com nome e com opção de paginação.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos encontrados.
	 */
	List<T> findByNativeQueryNamedParams(String sql, Paginacao paginacao, Map<String, Object> params);

	/**
	 * Pesquisar utilizando uma native query SQL contendo parâmetros sem nome e retornando único objeto.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos sem tipagem encontrados
	 */
	T findByNativeQuerySingleResult(String queryStr, Object... params);

	/**
	 * Pesquisar utilizando uma native query SQL contendo parâmetros sem nome e retornando objetos.
	 * 
	 * @param params
	 *            os parametros da query
	 * @param queryStr
	 *            a query SQL
	 * @return lista com os objetos sem tipagem encontrados
	 */
	List<?> findObjectsByNativeQuery(String sql, Object... params);

	/**
	 * Pesquisar por uma hql query, os parâmetros e são opcionais e retorna um resultado único.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	Object findSingleResult(String queryStr, Object... params);

	/**
	 * Pesquisar por uma named query, os parâmetros são opcionais e retorna um resultado único.
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	Object findSingleResultByNamedQuery(String namedQuery, Object... params);

	EntityManager getEntityManager();

	/**
	 * Atualiza o objeto de acordo com as informações do banco de dados.
	 * 
	 * @param entity
	 */
	void refresh(T entity);

	/**
	 * Cria o objeto no banco.
	 * 
	 * @param obj
	 *            o objeto
	 */
	void save(T obj);

	/**
	 * Define o tipo de Hint a utilizar no sistema.
	 * 
	 * Como exemplo, cache do Hibernate em 2LC (Second level cache), segue exemplo: stringHint =
	 * "org.hibernate.cacheable"; booleanHint = true;
	 * 
	 * @param stringHint
	 * @param booleanHint
	 */
	void setHint(String stringHint, boolean booleanHint);

	/**
	 * Define o total de resultados a serem exibidos nesta consulta
	 * 
	 * @param maxResults
	 */
	void setMaxResults(Integer maxResults);

	/**
	 * É obrigatório implementar o método para atribuir o tipo da classe persistente.
	 * 
	 * @param persistentClass
	 */
	void setPersistentClass(Class<T> persistentClass);

	/**
	 * Define o indice inicial da consulta
	 * 
	 * @param startingFrom
	 */
	void setStartingFrom(Integer startingFrom);

	/**
	 * Se o objeto existir, atualiza o objeto no banco. Se não existir cria-se o objeto no banco.
	 * 
	 * @param obj
	 *            o objeto
	 */
	void update(T obj);

	/**
	 * Atualizar registro utilizando uma sql nativa.
	 * 
	 * @param sql
	 * @param params
	 */
	void updateWithNativeQuery(String sql, Object... params);

}

