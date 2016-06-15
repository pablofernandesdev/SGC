package br.com.sgc.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Usuario;
import br.com.sgc.domain.Venda;
import br.com.sgc.service.UsuarioService;
import br.com.sgc.service.VendaService;
import br.com.sgc.util.Mensagens;

@Component("relatorioVendaVisao")
@Scope("request")
public class RelatorioVendaVisao implements Serializable {
	
	private static final String FW_RELATORIO_PERIODO_USUARIO = "/visao/relatorios/relatorioTotalPeriodoUsuario";

	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim;
	private Long codUsuario;
	
	private List<Venda> listaVendas;  
	private List<Usuario> listaUsuarios;
	
	private String nomeUsuario;
	private Long qtdVenda;
	private Double valorTotalVenda;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public String irTelaPesquisaRelatorioVenda(){
		setCodUsuario(null);
		setNomeUsuario(null);
		setListaUsuarios(usuarioService.findUsuarios(null));
		
		return FW_RELATORIO_PERIODO_USUARIO;
	}
	
	public void pesquisar(){
		if(isValidarPreenchimentoDataInicioFim()){
			setListaVendas(vendaService.findValorTotalVendaByPeriodoUsuario(getDataInicio(), getDataFim(), getCodUsuario()));
			if(!getListaVendas().isEmpty()){
				Double valorTotal = 0D;
				Long totalVendas = 0L;
				setNomeUsuario("");
				
				for(Venda venda : getListaVendas()){
					valorTotal = valorTotal + venda.getTotal();
					totalVendas++;
				}
				if(getCodUsuario() != null){
					Usuario usuario = new Usuario();
					usuario.setIdUsuario(getCodUsuario());
					setNomeUsuario(usuarioService.findUsuario(usuario).getNome());
				}
				setQtdVenda(totalVendas);
				setValorTotalVenda(valorTotal);
			}else{
				Mensagens.addError("MSG_A016");
			}
		}
	}
	
	private boolean isValidarPreenchimentoDataInicioFim() {
			if(getDataInicio() == null || getDataFim() == null){
				Mensagens.addError("MSG_E006");
				return false;
			}else{
				return true;
			}
			
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public List<Venda> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Venda> listaVendas) {
		this.listaVendas = listaVendas;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public VendaService getVendaService() {
		return vendaService;
	}

	public void setVendaService(VendaService vendaService) {
		this.vendaService = vendaService;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Long getQtdVenda() {
		return qtdVenda;
	}

	public void setQtdVenda(Long qtdVenda) {
		this.qtdVenda = qtdVenda;
	}

	public Double getValorTotalVenda() {
		return valorTotalVenda;
	}

	public void setValorTotalVenda(Double valorTotalVenda) {
		this.valorTotalVenda = valorTotalVenda;
	}
	
}
