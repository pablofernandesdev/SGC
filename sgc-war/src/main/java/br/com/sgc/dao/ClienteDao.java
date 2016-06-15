package br.com.sgc.dao;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Cliente;

public interface ClienteDao extends Serializable{

	public List<Cliente> findClientes(Cliente cliente);

	public void saveCliente(Cliente cliente);

	public void updateCliente(Cliente cliente);

	public void deleteCliente(Long idCliente);

	public Cliente findClienteCpf(String cpf);

}
