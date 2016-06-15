package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.EstadosBrasilEnum;
import br.com.sgc.domain.Fornecedor;
import br.com.sgc.service.FornecedorService;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.SgcUtil;
import br.com.sgc.validators.Validacoes;

@Component("manterFornecedorVisao")
@Scope("request")
public class ManterFornecedorVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_FORNECEDOR = "/visao/manterFornecedor/pesquisarFornecedor";
	private static final String FW_CADASTRAR_FORNECEDOR = "/visao/manterFornecedor/cadastrarFornecedor";
	private static final String FW_EDITAR_FORNECEDOR = "/visao/manterFornecedor/editarFornecedor";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedores;
	private List<EstadosBrasilEnum> estados;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	public String irTelaPesquisarFornecedor(){
		return FW_PESQUISAR_FORNECEDOR;
	}
	
	public String irTelaCadastrarFornecedor(){
		carregarListaEstados();
		return FW_CADASTRAR_FORNECEDOR;
	}
	
	public String voltarPesquisaFornecedor(){
		return FW_PESQUISAR_FORNECEDOR;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	private void carregarListaEstados() {
		for (EstadosBrasilEnum estado : EstadosBrasilEnum.values()) {
			getEstados().add(estado);
		}
	}

	public String irTelaEditarFornecedor(Fornecedor fornecedor){
		carregarListaEstados();
		setFornecedor(fornecedor);
		return FW_EDITAR_FORNECEDOR;
	}
	
	public void pesquisarFornecedor() {
		setListaFornecedores(fornecedorService.findFornecedores(getFornecedor()));
		if (getListaFornecedores().isEmpty()) {
				Mensagens.addError("MSG_E005");
		}	
	}
	
	public String saveFornecedor() {
		if (validarTelaFornecedor(true)) {
			removerMascaras();
			fornecedorService.saveFornecedor(getFornecedor());
			setFornecedor(new Fornecedor());

			return FW_PESQUISAR_FORNECEDOR;
		} else {
			return FW_CADASTRAR_FORNECEDOR;
		}
	}
	
	private boolean validarTelaFornecedor(boolean isNovoCadastro){
		boolean retorno = true;
		if (getFornecedor().getBairro() == null|| getFornecedor().getBairro().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.bairro"));
			retorno = false;
		}
		
		if (getFornecedor().getCidade() == null|| getFornecedor().getCidade().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if (getFornecedor().getCnpj() == null|| getFornecedor().getCnpj().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.cnpj"));
			retorno = false;
		}
		
		if (getFornecedor().getComplemento() == null|| getFornecedor().getComplemento().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.complemento"));
			retorno = false;
		}
		
		if (getFornecedor().getEmail() == null|| getFornecedor().getEmail().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if (getFornecedor().getLogradouro() == null|| getFornecedor().getLogradouro().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.logradouro"));
			retorno = false;
		}
		
		if (getFornecedor().getNomeFantasia() == null|| getFornecedor().getNomeFantasia().trim().equals("") || (getFornecedor().getNomeFantasia().length() < 2)) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.nomeFantasia"));
			retorno = false;
		}
		
		if (getFornecedor().getRazaoSocial() == null|| getFornecedor().getRazaoSocial().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.razaoSocial"));
			retorno = false;
		}
		
		if (getFornecedor().getTelCel() == null|| getFornecedor().getTelCel().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if (getFornecedor().getTelFixo() == null|| getFornecedor().getTelFixo().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if (getFornecedor().getUf() == null|| getFornecedor().getUf().trim().equals("")) {
			Mensagens.addError("MSG_E001",Mensagens.getValueProperties("label.uf"));
			retorno = false;
		}
		
		if((getFornecedor().getEmail().length()>0) && (Validacoes.validEmail(getFornecedor().getEmail()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if((getFornecedor().getCnpj().length()>0) && (Validacoes.validarCNPJ(getFornecedor().getCnpj()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cnpj"));
			retorno = false;
		}
		
		if((getFornecedor().getRazaoSocial().length()>0) && (Validacoes.validarNome(getFornecedor().getRazaoSocial()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.razaoSocial"));
			retorno = false;
		}
		
		if((getFornecedor().getCidade().length()>0) && (Validacoes.validarNome(getFornecedor().getCidade()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if((getFornecedor().getTelFixo().length()>0) && (Validacoes.validarTelefone(getFornecedor().getTelFixo()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if((getFornecedor().getTelCel().length()>0) && (Validacoes.validarTelefone(getFornecedor().getTelCel()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if(isNovoCadastro){
			//verifica se o cnpj informado já se encontra cadastrado no banco
			if(!getFornecedor().getCnpj().trim().equals("")){
				
				String cnpj = SgcUtil.removerMascara(getFornecedor().getCnpj());
				
				Fornecedor fornecedor = fornecedorService.findFornecedorCnpj(cnpj);
				
				if(fornecedor != null){
					Mensagens.addError("MSG_A012", Mensagens.getValueProperties("label.cnpj"));
					retorno = false;
				}
				
			}
			
		}
	
		return retorno;
	}
	

	public String updateFornecedor(){
		
		if(validarTelaFornecedor(false)){
			removerMascaras();
			fornecedorService.updateFornecedor(getFornecedor());
			setFornecedor(new Fornecedor());
		
			return FW_PESQUISAR_FORNECEDOR;
		} else {
			return FW_EDITAR_FORNECEDOR;
		}
	}
	
	
	private void removerMascaras(){
		getFornecedor().setCnpj(SgcUtil.removerMascara(getFornecedor().getCnpj()));
		getFornecedor().setTelCel(SgcUtil.removerMascara(getFornecedor().getTelCel()));
		getFornecedor().setTelFixo(SgcUtil.removerMascara(getFornecedor().getTelFixo()));
	}
	
	public void deleteFornecedor(Long idFornecedor){
		fornecedorService.deleteFornecedor(idFornecedor);
	}

	public Fornecedor getFornecedor() {
		if(fornecedor == null){
			fornecedor = new Fornecedor();
		}
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
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