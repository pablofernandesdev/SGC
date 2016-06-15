package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.ClienteDao;
import br.com.sgc.dao.VendaDao;
import br.com.sgc.domain.Cliente;
import br.com.sgc.domain.Venda;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.ClienteService;

@Service("clienteServiceImpl")
public class ClienteServiceImpl implements ClienteService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private VendaDao vendaDao;
	
	@Override
	public List<Cliente> findClientes(Cliente cliente) {
		return clienteDao.findClientes(cliente);
	}

	@Override
	public void saveCliente(Cliente cliente) {
		clienteDao.saveCliente(cliente);
	}

	@Override
	public void updateCliente(Cliente cliente) {
		clienteDao.updateCliente(cliente);
	}
	
	@Override
	public Cliente findClienteCpf(String cpf) {
		return clienteDao.findClienteCpf(cpf);
		
	}
	
	//verifica o registro do cliente com a venda, se for maior que 0 ele retorna uma mensagem de alerta, impedindo
	//a exclusão
	@Override
	public void deleteCliente(Long idCliente) {
		List<Venda> listVenda = vendaDao.findVendaByCliente(idCliente);
		
		if(listVenda.size() > 0){
			throw new NegocioException("MSG_A005");
		}
		
		clienteDao.deleteCliente(idCliente);
	}
	
}
