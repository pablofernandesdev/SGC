package br.com.sgc.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.sgc.domain.SaidaProduto;
import br.com.sgc.domain.Venda;

public interface VendaService extends Serializable {

	/**
	 * Este metodo é responsável por retornar as vendas
	 * @param venda
	 * @return
	 */
	public List<Venda> findVendas(Venda venda);

	public void saveVenda(Venda venda, List<SaidaProduto> listaSaidaProduto);

	public void updateVenda(Venda venda);

	public void deleteVenda(Long idVenda);

	public List<SaidaProduto> findSaidaProdutoByIdVenda(Long idVenda);
	
	public List<Venda> findValorTotalVendaByPeriodoUsuario(Date dataInicio, Date dataFim , Long codUsuario);

}