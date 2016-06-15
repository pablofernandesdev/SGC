package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Categoria;

public interface CategoriaDao extends Serializable{

	public List<Categoria> findCategorias(Categoria categoria);

	public void saveCategoria(Categoria categoria);

	public void updateCategoria(Categoria categoria);

	public void deleteCategoria(Long idCategoria);



}
