package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Empresa;

public interface EmpresaDao extends Serializable{

	public List<Empresa> findEmpresas(Empresa empresa);

	public void saveEmpresa(Empresa empresa);

	public void updateEmpresa(Empresa empresa);

	public void deleteEmpresa(Long idEmpresa);

	public Empresa findEmpresaCnpj(String cnpj);

}
