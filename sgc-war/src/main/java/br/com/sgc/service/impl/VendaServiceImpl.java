package br.com.sgc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.VendaDao;
import br.com.sgc.domain.SaidaProduto;
import br.com.sgc.domain.Venda;
import br.com.sgc.service.VendaService;

@Service("vendaServiceImpl")
public class VendaServiceImpl implements VendaService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VendaDao vendaDao;
	
	@Override
	public List<Venda> findVendas(Venda venda) {
		return vendaDao.findVendas(venda);
	}

	@Override
	public void saveVenda(Venda venda, List<SaidaProduto> listaSaidaProduto) {
		vendaDao.saveVenda(venda, listaSaidaProduto);
	}

	@Override
	public void updateVenda(Venda venda) {
		vendaDao.updateVenda(venda);
	}
	
	@Override
	public void deleteVenda(Long idVenda) {
		vendaDao.deleteVenda(idVenda);
	}
	
	@Override
	public List<SaidaProduto> findSaidaProdutoByIdVenda(Long idVenda) {
		return vendaDao.findSaidaProdutoByIdVenda(idVenda);
	}

	public List<Venda> findValorTotalVendaByPeriodoUsuario(Date dataInicio, Date dataFim , Long codUsuario){
		return vendaDao.findValorTotalVendaByPeriodoUsuario(dataInicio, dataFim, codUsuario);
	}
}
