package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.EntradaProduto;
import br.com.sgc.domain.Fornecedor;
import br.com.sgc.domain.Produto;
import br.com.sgc.service.EntradaProdutoService;
import br.com.sgc.service.FornecedorService;
import br.com.sgc.service.ProdutoService;
import br.com.sgc.util.Mensagens;

@Component("manterEntradaProdutoVisao")   
@Scope("request")
public class ManterEntradaProdutoVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_ENTRADA_PRODUTO = "/visao/manterEntradaProduto/pesquisarEntradaProduto";
	private static final String FW_CADASTRAR_ENTRADA_PRODUTO = "/visao/manterEntradaProduto/cadastrarEntradaProduto";
	private static final String FW_PESQUISAR_PRODUTO_FORNECEDOR = "/visao/relatorios/relatorioProdutoFornecedor";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private EntradaProduto entradaProduto;
	private List<EntradaProduto> listaEntradaProduto;
	private List<Fornecedor> listaFornecedores;
	
	@Autowired
	private EntradaProdutoService entradaProdutoService;  
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	private List<Produto> listProdutos;
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	public String voltarPesquisaEntradaProduto(){
		return FW_PESQUISAR_ENTRADA_PRODUTO;
	}
	
	public String irTelaPesquisarEntradaProduto(){
		return FW_PESQUISAR_ENTRADA_PRODUTO;
	}
	
	public String irTelaPesquisarEntrada(){
		return FW_PESQUISAR_PRODUTO_FORNECEDOR;
	}
	
	public String irTelaCadastrarEntradaProduto(Produto produto){
		
		getEntradaProduto().setProduto(produtoService.findProdutos(produto).get(0));
		getEntradaProduto().setIdProduto(produto.getIdProduto());
		carregarListaTodosFornecedores();
		
		setListaEntradaProduto(entradaProdutoService.findListaEntradaProdutoByProduto(produto.getIdProduto()));
		
		return FW_CADASTRAR_ENTRADA_PRODUTO;
	}

	public String saveEntradaProduto() {
		if (validarTelaEntradaProduto()) {
			
			if(getEntradaProduto().getIdFornecedor() != null){
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(getEntradaProduto().getIdFornecedor());
				getEntradaProduto().setFornecedor(fornecedor);
			}else{
				getEntradaProduto().setFornecedor(null);
			}
				
			entradaProdutoService.saveEntradaProduto(getEntradaProduto());
			getEntradaProduto().setIdEntradaProduto(null);
			getEntradaProduto().setData(null);
			getEntradaProduto().setQuantidadeEntrada(null);
			return irTelaCadastrarEntradaProduto(entradaProduto.getProduto());
		}
		
		return FW_CADASTRAR_ENTRADA_PRODUTO;
	}
	
	public void pesquisarEntrada(){
	
		if(validarTelaPesquisar()){
			pesquisarSemValidar();
			if(getListProdutos().isEmpty()){
				Mensagens.addError("MSG_E005");
			}
		}
	}
	
	private void pesquisarSemValidar(){
		setListProdutos(produtoService.findProdutosByFornecedor(getEntradaProduto().getFornecedor()));
	}
	
	private boolean validarTelaPesquisar(){
		boolean retorno = true;
		if ((getEntradaProduto().getFornecedor().getRazaoSocial() == null|| getEntradaProduto().getFornecedor().getRazaoSocial().trim().equals(""))
				&&  (getEntradaProduto().getFornecedor().getCnpj() == null|| getEntradaProduto().getFornecedor().getCnpj().trim().equals(""))) {
			Mensagens.addError("MSG_A008");
			setListProdutos(null);
			retorno = false;
		}
		
		
		return retorno;
	}
	
	
	public String excluirEntradaProduto(EntradaProduto entradaProduto){
		
		entradaProdutoService.deleteEntradaProduto(entradaProduto);
		return irTelaCadastrarEntradaProduto(entradaProduto.getProduto());
	}

	private boolean validarTelaEntradaProduto() {
		boolean retorno = true;
		if (getEntradaProduto().getQuantidadeEntrada() == null) {
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.quantidade.entrada"));
			retorno = false;
		}
		
		if(getEntradaProduto().getData() == null){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.data"));
			retorno = false;
		}
		
		return retorno;
	}

	public EntradaProduto getEntradaProduto() {
		if(entradaProduto == null){   
			entradaProduto = new EntradaProduto();
		}
		return entradaProduto;
	}

	public void setEntradaProduto(EntradaProduto entradaProduto) {
		this.entradaProduto = entradaProduto;
	}
	
	private void carregarListaTodosFornecedores() {
		setListaFornecedores(fornecedorService.findFornecedores(null));
	}
	
	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}
	

	public List<EntradaProduto> getListaEntradaProduto() {
		if(listaEntradaProduto == null){
			listaEntradaProduto = new ArrayList<EntradaProduto>();
		}
		
		return listaEntradaProduto;
	}

	public void setListaEntradaProduto(List<EntradaProduto> listaEntradaProduto) {
		this.listaEntradaProduto = listaEntradaProduto;
	}

	public List<Produto> getListProdutos() {
		return listProdutos;
	}

	public void setListProdutos(List<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}

}