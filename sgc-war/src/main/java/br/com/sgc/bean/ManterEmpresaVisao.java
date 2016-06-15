package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.EstadosBrasilEnum;
import br.com.sgc.domain.Empresa;
import br.com.sgc.service.EmpresaService;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.SgcUtil;
import br.com.sgc.validators.Validacoes;

@Component("manterEmpresaVisao")
@Scope("request")
public class ManterEmpresaVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_EMPRESA = "/visao/manterEmpresa/pesquisarEmpresa";
	private static final String FW_CADASTRAR_EMPRESA = "/visao/manterEmpresa/cadastrarEmpresa";
	private static final String FW_EDITAR_EMPRESA = "/visao/manterEmpresa/editarEmpresa";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Empresa empresa;
	private List<Empresa> listaEmpresas;
	private List<EstadosBrasilEnum> estados;
	
	@Autowired
	private EmpresaService empresaService;
	
	public String irTelaPesquisarEmpresa(){
		setListaEmpresas(empresaService.findEmpresas(null));
		setEmpresa(empresa);
		return FW_PESQUISAR_EMPRESA;
	}
	
	public String irTelaCadastrarEmpresa(){
		carregarListaEstados();
		return FW_CADASTRAR_EMPRESA;
	}
	
	public String voltarPesquisaEmpresa(){
		return FW_PESQUISAR_EMPRESA;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	private void carregarListaEstados() {
		for (EstadosBrasilEnum estado : EstadosBrasilEnum.values()) {
			getEstados().add(estado);
		}
	}

	public String irTelaEditarEmpresa(Empresa empresa){
		carregarListaEstados();
		setEmpresa(empresa);
		return FW_EDITAR_EMPRESA;
	}
	
	public void pesquisarEmpresa() {
		setListaEmpresas(empresaService
				.findEmpresas(getEmpresa()));
		if (getListaEmpresas().isEmpty()) {
			Mensagens.addError("MSG_E005");
		}

	}
	
	public String saveEmpresa() {
		if (validarTelaEmpresa(true)) {
			removerMascaras();
			empresaService.saveEmpresa(getEmpresa());
			setEmpresa(new Empresa());

			pesquisarEmpresa();

			return FW_PESQUISAR_EMPRESA;
		} else {
			return FW_CADASTRAR_EMPRESA;
		}
	}
	
	private boolean validarTelaEmpresa(boolean isNovoCadastro){
		boolean retorno = true;
		if (getEmpresa().getBairro() == null|| getEmpresa().getBairro().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.bairro"));
			retorno = false;
		}
		
		if (getEmpresa().getCidade() == null|| getEmpresa().getCidade().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if (getEmpresa().getCnpj() == null|| getEmpresa().getCnpj().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.cnpj"));
			retorno = false;
		}
		
		if (getEmpresa().getComplemento() == null|| getEmpresa().getComplemento().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.complemento"));
			retorno = false;
		}
		
		if (getEmpresa().getEmail() == null|| getEmpresa().getEmail().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if (getEmpresa().getLogradouro() == null|| getEmpresa().getLogradouro().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.logradouro"));
			retorno = false;
		}
		
		if (getEmpresa().getNomeFantasia() == null|| getEmpresa().getNomeFantasia().trim().equals("") || (getEmpresa().getNomeFantasia().length() < 2)) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.nomeFantasia"));
			retorno = false;
		}
		
		if (getEmpresa().getRazaoSocial() == null|| getEmpresa().getRazaoSocial().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.razaoSocial"));
			retorno = false;
		}
		
		if (getEmpresa().getTelCel() == null|| getEmpresa().getTelCel().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if (getEmpresa().getTelFixo() == null|| getEmpresa().getTelFixo().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if (getEmpresa().getUf() == null|| getEmpresa().getUf().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.uf"));
			retorno = false;
		}
		
		if((getEmpresa().getEmail().length()>0) && (Validacoes.validEmail(getEmpresa().getEmail()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if((getEmpresa().getCnpj().length()>0) && (Validacoes.validarCNPJ(getEmpresa().getCnpj()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cnpj"));
			retorno = false;
		}
		
		if((getEmpresa().getRazaoSocial().length()>0) && (Validacoes.validarNome(getEmpresa().getRazaoSocial()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.razaoSocial"));
			retorno = false;
		}
		
		if((getEmpresa().getCidade().length()>0) && (Validacoes.validarNome(getEmpresa().getCidade()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if((getEmpresa().getTelFixo().length()>0) && (Validacoes.validarTelefone(getEmpresa().getTelFixo()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if((getEmpresa().getTelCel().length()>0) && (Validacoes.validarTelefone(getEmpresa().getTelCel()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if(isNovoCadastro){
			//verifica se o cnpj informado já se encontra cadastrado no banco
			if(!getEmpresa().getCnpj().trim().equals("")){
				
				String cnpj = SgcUtil.removerMascara(getEmpresa().getCnpj());
				
				Empresa empresa = empresaService.findEmpresaCnpj(cnpj);
				
				if(empresa != null){
					Mensagens.addError("MSG_A012", Mensagens.getValueProperties("label.cnpj"));
					retorno = false;
				}
				
			}
			
		}
	
		return retorno;
	}
	
	public String updateEmpresa(){
		if(validarTelaEmpresa(false)){
			removerMascaras();
			empresaService.updateEmpresa(getEmpresa());
			setEmpresa(new Empresa());
			pesquisarEmpresa();
		
			return FW_PESQUISAR_EMPRESA;
		} else {
			return FW_EDITAR_EMPRESA;
		}
	}
	
	private void removerMascaras(){
		getEmpresa().setCnpj(SgcUtil.removerMascara(getEmpresa().getCnpj()));
		getEmpresa().setTelCel(SgcUtil.removerMascara(getEmpresa().getTelCel()));
		getEmpresa().setTelFixo(SgcUtil.removerMascara(getEmpresa().getTelFixo()));
	}
	
	public void deleteEmpresa(Long idEmpresa){
		empresaService.deleteEmpresa(idEmpresa);
		pesquisarEmpresa();
	}

	public Empresa getEmpresa() {
		if(empresa == null){
			empresa = new Empresa();
		}
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public List<EstadosBrasilEnum> getEstados() {
		if (estados == null) {
			estados = new ArrayList<EstadosBrasilEnum>();
		}

		return estados;
	}

	public void setEstados(List<EstadosBrasilEnum> estados) {
		this.estados = estados;
	}
	
}