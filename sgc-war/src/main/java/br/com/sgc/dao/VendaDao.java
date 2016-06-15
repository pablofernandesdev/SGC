package br.com.sgc.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.sgc.domain.SaidaProduto;
import br.com.sgc.domain.Venda;

public interface VendaDao extends Serializable{

	public List<Venda> findVendas(Venda venda);

	public void saveVenda(Venda venda, List<SaidaProduto> listaSaidaProduto);

	public void updateVenda(Venda venda);

	public void deleteVenda(Long idVenda);
	
	public List<Venda> findVendaByEmpresa(Long idEmpresa);

	public List<Venda> findVendaByCliente(Long idCliente);

	public List<Venda> findVendaByUsuario(Long idUsuario);

	public List<SaidaProduto> findSaidaProdutoByIdVenda(Long idVenda);
	
	public List<Venda> findValorTotalVendaByPeriodoUsuario(Date dataInicio, Date dataFim , Long codUsuario);

}
