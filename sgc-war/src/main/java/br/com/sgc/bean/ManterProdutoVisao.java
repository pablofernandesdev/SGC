package br.com.sgc.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Categoria;
import br.com.sgc.domain.Produto;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.CategoriaService;
import br.com.sgc.service.ProdutoService;
import br.com.sgc.util.Mensagens;

@Component("manterProdutoVisao") 
@Scope("request")
public class ManterProdutoVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_PRODUTO = "/visao/manterProduto/pesquisarProduto";
	private static final String FW_CADASTRAR_PRODUTO = "/visao/manterProduto/cadastrarProduto";
	private static final String FW_EDITAR_PRODUTO = "/visao/manterProduto/editarProduto";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Produto produto;
	private List<Produto> listaProdutos;
	private List<Categoria> listaAllCategoria;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	public String irTelaPesquisarProduto(){
		return FW_PESQUISAR_PRODUTO;
	}
	
	public String irTelaCadastrarProduto(){
		
		setProduto(null);
		carregarListaTodasCategorias();
		
		getProduto().setMargemLucro(0D);  
		getProduto().setPrecoCusto(0D);
		
		return FW_CADASTRAR_PRODUTO;
	}

	public String irTelaEditarProduto(Produto produto){
		
		setProduto(produto);
		carregarListaTodasCategorias();
		
		return FW_EDITAR_PRODUTO;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	public void pesquisarProduto() {

		setListaProdutos(produtoService.findProdutos(getProduto()));
		if (getListaProdutos().isEmpty()) {
			Mensagens.addError("MSG_E005");
		}

	}
	
	public String saveProduto() {
		if (validarTelaProduto()) {

			produtoService.saveProduto(getProduto());
			setProduto(new Produto());

			pesquisarProduto();

			return FW_PESQUISAR_PRODUTO;

		} else {
			return FW_CADASTRAR_PRODUTO;
		}
	}

	public String voltarPesquisaProduto(){
		return FW_PESQUISAR_PRODUTO;
	}
	
	private boolean validarTelaProduto(){
		boolean retorno = true;
		if(getProduto().getDescricao() == null || getProduto().getDescricao().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.descricao"));
			retorno = false;
		}
		
		if(getProduto().getMarca() == null || getProduto().getMarca().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.marca"));
			retorno = false;
		}
		
		if(getProduto().getNome() == null || getProduto().getNome().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.nome"));
			retorno = false;
		}
		
		if(getProduto().getMargemLucro() == null || getProduto().getMargemLucro()<=0){ 
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.margemLucro"));
			retorno = false;
		}
		
		if(getProduto().getPrecoVenda() == null || getProduto().getPrecoVenda()<=0){ 
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.precoVenda"));
			retorno = false;
		}
		
		if(getProduto().getPrecoCusto() == null || getProduto().getPrecoCusto() <=0){ 
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.precoCusto"));
			retorno = false;
		}
		
		if(getProduto().getIdCategoria() == null ){ 
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.categoria"));
			retorno = false;
		}
			
		return retorno;
	}
	
	public void calcularPrecoVenda (){

		Boolean retorno = true;
		if(getProduto().getMargemLucro() == null ){ 
			retorno = false;
		}
		
		if(getProduto().getPrecoCusto() == null ){ 
			retorno = false;
		}
		
		if(retorno){
			Double resultado = new Double ((getProduto().getMargemLucro() * getProduto().getPrecoCusto())/100) + getProduto().getPrecoCusto();
			produto.setPrecoVenda(resultado);
		}
	}
	
	public String updateProduto() {

		if (validarTelaProduto()) {
			produtoService.updateProduto(getProduto());

			setProduto(new Produto());

			pesquisarProduto();

			return FW_PESQUISAR_PRODUTO;
		} else {
			return FW_EDITAR_PRODUTO;
		}

	}
	
	public void deleteProduto(Long idProduto){
		try{
			produtoService.deleteProduto(idProduto);
			pesquisarProduto();
		}catch(NegocioException ne){
			Mensagens.addError(ne.getMessage());
		}
	}
	
	private void carregarListaTodasCategorias() {
		setListaAllCategoria(categoriaService.findCategorias(null));
	}
	
	public Produto getProduto() {
		if(produto == null){
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Categoria> getListaAllCategoria() {
		return listaAllCategoria;
	}
	
	public void setListaAllCategoria(List<Categoria> listaAllCategoria) {
		this.listaAllCategoria = listaAllCategoria;
	}

}