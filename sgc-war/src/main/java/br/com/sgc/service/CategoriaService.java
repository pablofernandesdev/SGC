package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Categoria;

public interface CategoriaService extends Serializable {

	/**
	 * Este metodo é responsável por retornar as categorias
	 * @param categoria
	 * @return
	 */
	public List<Categoria> findCategorias(Categoria categoria);

	public void saveCategoria(Categoria categoria);

	public void updateCategoria(Categoria categoria);

	public void deleteCategoria(Long idCategoria);

}