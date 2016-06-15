package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.EmpresaDao;
import br.com.sgc.domain.Empresa;
import br.com.sgc.service.EmpresaService;

@Service("empresaServiceImpl")
public class EmpresaServiceImpl implements EmpresaService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmpresaDao empresaDao;
	
	@Override
	public List<Empresa> findEmpresas(Empresa empresa) {
		return empresaDao.findEmpresas(empresa);
	}

	@Override
	public void saveEmpresa(Empresa empresa) {
		empresaDao.saveEmpresa(empresa);
	}

	@Override
	public void updateEmpresa(Empresa empresa) {
		empresaDao.updateEmpresa(empresa);
	}
	
	@Override
	public void deleteEmpresa(Long idEmpresa) {
		empresaDao.deleteEmpresa(idEmpresa);
	}
	
	@Override
	public Empresa findEmpresaCnpj(String cnpj) {
		return empresaDao.findEmpresaCnpj(cnpj);
		
	}
	
}
