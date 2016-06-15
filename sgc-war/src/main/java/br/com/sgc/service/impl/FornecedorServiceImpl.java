package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.FornecedorDao;
import br.com.sgc.domain.Fornecedor;
import br.com.sgc.service.FornecedorService;

@Service("fornecedorServiceImpl")
public class FornecedorServiceImpl implements FornecedorService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FornecedorDao fornecedorDao;
	
	@Override
	public List<Fornecedor> findFornecedores(Fornecedor fornecedor) {
		return fornecedorDao.findFornecedores(fornecedor);
	}

	@Override
	public void saveFornecedor(Fornecedor fornecedor) {
		fornecedorDao.saveFornecedor(fornecedor);
	}

	@Override
	public void updateFornecedor(Fornecedor fornecedor) {
		fornecedorDao.updateFornecedor(fornecedor);
	}
	
	@Override
	public void deleteFornecedor(Long idFornecedor) {
		fornecedorDao.deleteFornecedor(idFornecedor);
	}
	
	@Override
	public Fornecedor findFornecedorCnpj(String cnpj) {
		return fornecedorDao.findFornecedorCnpj(cnpj);
		
	}
	
	
	
}
