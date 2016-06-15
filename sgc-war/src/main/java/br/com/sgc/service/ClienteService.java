package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Cliente;


public interface ClienteService extends Serializable {

	public List<Cliente> findClientes(Cliente cliente);

	public void saveCliente(Cliente cliente);

	public void updateCliente(Cliente cliente);

	public void deleteCliente(Long idCliente);
	
	public Cliente findClienteCpf(String cpf);

}