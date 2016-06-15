if(window.addEventListener) {
    window.addEventListener("load", function() { blinkIt();}, false);
} else {
    if(window.attachEvent) {
    window.attachEvent("onload", function() { blinkIt(); });
    }
}

//metodo que nao deixa apertar o F5 para atualizar
noRefresh = function(e){
    var version = navigator.appVersion;
    var keycode = (window.event) ? event.keyCode : e.keyCode;
    
    if ((version.indexOf('MSIE') != -1)) {
        if (keycode == 116) {
            event.keyCode = 0;
            event.returnValue = false;
           // alert('F5 pressionado no IE');
            return false;
        }
    }
    else {
        if (keycode == 116) {
          //  alert('F5 pressionado no FF');
            return false;
        }
    }
}

/* Função utilizada para verificar erros no modalPanel
 * */
function ajaxRequestContainsErrors() {
    return document.getElementById("maximumSeverity").value == "2" || document.getElementById("maximumSeverity").value == "3";
}

/*
 * @revision 001
 * @author fernando.gom
 * @reason [IMP-ET210]
 * 
 * Função utilizada para verificar mensagens de alerta
 */
function ajaxRequestContainsWarnings() {
    return document.getElementById("maximumSeverity").value == "1";
}

function abrirModalSeErro(idModal){
	if(ajaxRequestContainsErrors())
		Richfaces.showModalPanel(idModal);
}

function scrollTop(){
	window.scrollTo(0,0);
}

function formataDado(campo,tammax,pos,teclapres){
	var keyCode;
	if (teclapres.srcElement)
		keyCode = teclapres.keyCode;
	else if (teclapres.target)
		keyCode = teclapres.which;
	
	if (keyCode == 0 || keyCode == 8 || keyCode == 13)
		return true;
		
	if ((keyCode < 48 || keyCode > 57) && (keyCode != 88) && (keyCode != 120))
		return false;

		var tecla = keyCode;
		vr = campo.value;
		vr = vr.replace( "-", "" );
		vr = vr.replace( "/", "" );
		tam = vr.length ;
		
		if (tam < tammax && tecla != 8){ tam = vr.length + 1 ; }
		
		if (tecla == 8 ){ tam = tam - 1 ; }
		if ( tecla == 8 || tecla == 88 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 || tecla == 120){
			if ( tam <= 2 ){
				campo.value = vr ;}
			if ( tam > pos && tam <= tammax ){
				campo.value = vr.substr( 0, tam - pos ) + '-' + vr.substr( tam - pos, tam );}
		}
}

// Para utilizar a função formataMascara, deve-se incluir no campo as propriedades conf. abaixo:
//onkeypress="return formataMascara(this,cnpj)" onblur="return formataMascara(this,cnpj)" 

function formataMascara(objeto, funcao) {
	v_obj = objeto
	v_fun = funcao
	setTimeout("execmascara()", 1)
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}

function leech(v) {
	v = v.replace(/o/gi, "0")
	v = v.replace(/i/gi, "1")
	v = v.replace(/z/gi, "2")
	v = v.replace(/e/gi, "3")
	v = v.replace(/a/gi, "4")
	v = v.replace(/s/gi, "5")
	v = v.replace(/t/gi, "7")
	return v
}

function soNumeros(v) {

	v = v.replace(/\D/g, "")
	return v
}

function caae(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/^(\d{8})(\d)/, "$1.$2") 
											
	v = v.replace(/^(\d{8})\.(\d{1})(\d)/, "$1.$2.$3") 
														
	v = v.replace(/^(\d{8})\.(\d{1}).(\d{4})(\d)/, "$1.$2.$3.$4")
	return v
}

function soLetrasEPonto(v) {

	v = v.replace(/[^a-záàâãéèêíïóôõöúüçñA-ZÁÀÂÃÉÈÍÏÓÔÕÖÚÜÇÑ .]+/g,'')
	return v
}

function telefone(v) {
	v=v.replace(/\D/g,"");             //Remove tudo o que não é dígito
    v=v.replace(/^(\d{2})(\d)/g,"($1)$2"); //Coloca parênteses em volta dos dois primeiros dígitos
    v=v.replace(/(\d)(\d{4})$/,"$1-$2");    //Coloca hífen entre o quarto e o quinto dígitos
    return v;
}

function cpf(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/, "$1.$2") // Coloca ponto entre o segundo e o
											// terceiro dígitos
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3") // Coloca ponto entre o
														// quinto e o sexto
														// dígitos
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2") // Coloca uma barra entre o
												// oitavo e o nono dígitos
	v = v.replace(/(\d{4})(\d)/, "$1-$2") // Coloca um hífen depois do bloco
											// de quatro dígitos	
	return v
}



function cep(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/(\d{2})(\d)/, "$1.$2") // Coloca um ponto entre o segundo e
											// o terceiro dígitos
	v = v.replace(/(\d{3})(\d)/, "$1-$2") // Coloca um traço entre o quinto e
											// o sexto dígitos
	return v
}


function cnpj(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/, "$1.$2") // Coloca ponto entre o segundo e o
											// terceiro dígitos
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3") // Coloca ponto entre o
														// quinto e o sexto
														// dígitos
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2") // Coloca uma barra entre o
												// oitavo e o nono dígitos
	v = v.replace(/(\d{4})(\d)/, "$1-$2") // Coloca um hífen depois do bloco
											// de quatro dígitos
	return v
}

function data(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/, "$1/$2") // Coloca uma barra entre o segundo
											// e o terceiro dígito
	v = v.replace(/^(\d{2})\/(\d{2})(\d{1})/, "$1/$2/$3") // Coloca uma barra
															// entre o quarto e
															// o quinto dígito
	return v.substring(0, 10);
}

function date(v) {
	return data(v);
}

function dataQuatroDigitos(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/, "$1/$2") // Coloca uma barra entre o segundo
											// e o terceiro dígito
	return v
}

function Valida_Data(componente){
	   var este = componente;
	   var retorno = false;
	   var dia = 0; var mes = 0; var ano=0;

	   if (este.value.length==0) retorno=true;
	   else if (este.value.length>=6){
	    // primeiro verifica se nenhum '/' foi digitado.
	    // em caso positivo acrescenta as barras
	    var posbarra=-1;
	    for(var posini=0; posini<este.value.length; posini++){
	      if (este.value.substring(posini,posini+1)=='/') {
			posbarra=posini;
			break;
		  }
	    }

	    // se nao encontrou, coloca as barras
	    if (posbarra<0){
		// se for dmyyyy
	      if (este.value.length==6) este.value = '0'+este.value.substring(0,1)+'/'+'0'+este.value.substring(1,2)+'/'+este.value.substring(2);
	      else if (este.value.length==7) este.value = este.value.substring(0,2)+'/'+'0'+este.value.substring(2,3)+'/'+este.value.substring(3);
	      else if (este.value.length==8) este.value = este.value.substring(0,2)+'/'+este.value.substring(2,4)+'/'+este.value.substring(4);
	    }

	    // recupera o dia
	    var posicao=0;posicao2=0;
	    while (posicao<este.value.length){
	      if (este.value.substring(posicao,posicao+1)=='/') break;
	      posicao++;
	    }
	    dia = (este.value.substring(0,posicao));
	    if (dia!='') dia = dia*1;

	    // recupera o mes
	    var posicao2=posicao+1;
	    while (posicao2<este.value.length){
	      if (este.value.substring(posicao2,posicao2+1)=='/') break;
	      posicao2++;
	    }
	    mes = (este.value.substring(posicao+1,posicao2));
	    if (mes!='') mes = mes*1;

	    // recupera o ano
	    ano = (este.value.substring(posicao2+1));
	    if (ano!='') ano = ano*1;

	    // define dia maximo do mes
	    var diaMaximoMes = 31;
	    if ((mes==4)||(mes==6)||(mes==9)||(mes==11)) diaMaximoMes = 30;
	    else if (mes==2){
	      if ((ano%4)==0) diaMaximoMes = 29;
	      else diaMaximoMes = 28;
	    }
	    retorno = ((este.value.length<=10)&&(dia>0)&&(dia<=diaMaximoMes)&&(mes>0)&&(mes<=12)&&(ano>=1880));
	   }
	   else retorno = false;
	   return retorno;
	}

function limitaQtdCaracter(campo,tammax,teclapres){
	if (teclapres.srcElement)
		keyCode = teclapres.keyCode;
	else if (teclapres.target)
		keyCode = teclapres.which;
	
	if (keyCode != 37 && keyCode != 38 && keyCode != 39 && keyCode != 40){
		campo.value = campo.value.substring(0, tammax);
	}
	
	var qtd = campo.value.length;
	campo.focus();
	campo.setSelectionRange(qtd,qtd);
}

function startCountdown() {
	if (seconds<=0) {
        seconds=60;
        minutos-=1;
    }
    if (minutos<=-1) {
        seconds=0;
        seconds+=1;
        campo.innerHTML="";
        campo.innerHTML="Sessão expirada!";
        sairLogout();
    } else {
        seconds-=1;
        if(seconds < 10) seconds = "0" + seconds; 
        campo.innerHTML = " " + minutos+"min "+seconds;
        window.setTimeout('startCountdown()', 1000);
    }
}

function contarCaracteresOnload(nomeCampo, nomeContador, tamanho) {
	quantidade = 0;
	restantes = tamanho;
	
	if (document.getElementById(nomeCampo) != null && document.getElementById(nomeCampo).value != null ) {
		restantes = tamanho - parseInt(document.getElementById(nomeCampo).value.length);
	}
	
	document.getElementById(nomeContador).innerHTML = 'Caracteres restantes: <b>' + restantes + '</b>';
}

function contarCaracteres(objeto, e, nomeContador, tamanho) {
	// Edita o valor do textarea, considerando apenas o tamanho caracteres
	
	var fieldLength = 0;
	fieldLength = parseInt(objeto.value.length);
	if(fieldLength > tamanho) {
		objeto.value = objeto.value.substring(0,tamanho);
	}
	
	// inicialização das variáveis utilizadas
	var quantidade = 0;
	var restantes = 0;
	
	quantidade = parseInt(objeto.value.length); // Aqui verificamos quantos caracteres l digitados
	restantes = tamanho - quantidade; // A conta que nos informará quantos caracteres restam
	
	// Se ainda tiver caracteres disponíveis, então
	if (restantes > 0) {
		// Seta um novo texto no div "Caracteres restantes: XX"
		if(nomeContador != null) {
			document.getElementById(nomeContador).innerHTML = 'Caracteres restantes: <b>' + restantes + '</b>';
		}
	} else {
		if(nomeContador != null) {
			document.getElementById(nomeContador).innerHTML = 'Caracteres restantes: <b>' + restantes + '</b>';
		}
	}
	
	// Captura o evento (ação de digitar) e faz a validação necessária
	// para funcionar no Internet Explorer e Firefox/Chrome
	
	var nav4 = window.Event ? true : false;
	if (window.event) { // Internet Explorer
		nav4 = event.keyCode;
	} else { // Firefox
		nav4 = e.which;
	}
	// alert('nav4 >> ' + nav4);
	
	// Se for backspace, delete, enter ou tab, deixa a digitação passar
	if(nav4 == 8 || nav4 == 0 || nav4 == 32 || nav4 == 9) {
		return true;
	} 
	
	return false; // Se não, cancela o evento, matando o caractere digitado
}

/*Função que padroniza HORA*/
function Hora(v){
	v=v.replace(/\D/g,"");
	v=v.replace(/(\d{2})(\d)/,"$1:$2");
	return v;
}

/*Função que padroniza valor monétario*/
function Valor(v, d){
	var d = (d) ? d : 2;
	v=v.replace(/\D/g,"");												//permite digitar apenas números
	v=v.replace(new RegExp("[0-9]{" + (13+d) + "}"),"");				//limita pra máximo 999.999.999.999,d
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (9+d) + "})$"),"$1.$2");	//coloca ponto antes dos últimos 9 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (6+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 6 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (3+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 3 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{1," + d + "})$"), "$1,$2");	//coloca virgula antes dos últimos d digitos
	return v;
}

/*
/* Função que padroniza valor monétario
 * Forma de usar: a chamada da funcão deve ser dentro do formataMascara exemplo abaixo:
 * onkeypress="return formataMascara(this,Inteiro)" onblur="return formataMascara(this,Inteiro)"
 */
function Inteiro(v, d){
	var d = (d) ? d : 2;
	
	v=v.replace(/\D/g,"");												//permite digitar apenas números
	v=v.replace(new RegExp("[0-9]{" + (11+d) + "}"),"");				//limita pra máximo 999.999.999.999,d
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (7+d) + "})$"),"$1.$2");	//coloca ponto antes dos últimos 9 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (4+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 6 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (1+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 3 + d digitos
	//v=v.replace(new RegExp("(\\d{1})(\\d{1," + d + "})$"), "$1,$2");	//coloca virgula antes dos últimos d digitos
	return v;
}

/*Função que padroniza valor monétario*/
function ValorOrcamento(v, d){
	var d = (d) ? d : 2;
	v=v.replace(/\D/g,"");												//permite digitar apenas números
	v=v.replace(new RegExp("[0-9]{" + (10+d) + "}"),"");				//limita pra máximo 999.999.999,d
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (6+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 6 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{" + (3+d) + "})$"), "$1.$2");	//coloca ponto antes dos últimos 3 + d digitos
	v=v.replace(new RegExp("(\\d{1})(\\d{1," + d + "})$"), "$1,$2");	//coloca virgula antes dos últimos d digitos
	return v;
}

function setFocus(id){
// A ARVORE DE JSF NAO DEIXA CONSISTENTE TODA ARVORE DO DOM, SE UM ELEMENTO (Panel) ESTA NOT VISIBLE , TODO SEUS FIILHOS SERAO MAS NAO ESTARA CONFIGURADO NA ARVORE DO DOM.

}

/*Função que bloqueia o copiar(ctrl+c) e colar(ctrl+v) do teclado*/
function validateKey (evt){
	var ctrl=window.event.ctrlKey;
	var tecla=window.event.keyCode;

	if (ctrl && tecla==67) {
		event.keyCode=0; 
		event.returnValue=false;
	}

	if (ctrl && tecla==86) {
		event.keyCode=0;
		event.returnValue=false;
	}
		
    return true;
			
} 

/* Seleciona todos os checkBox */
function seleciona(){
	var form = document.idPesquisaProjeto;
	for(i=1;i<form.elements.length;i++){
		if(form.elements[i].type == 'checkbox') {
	    		form.elements[i].checked = true;
	    }
	}
}

function blinkIt() {
	if(document.getElementById('idFormVisualizarPaineldeControle') != null) {
		var qtdEmail = document.getElementById('idFormVisualizarPaineldeControle:idQtdMsgEmail');
		qtdEmail.style.visibility=(qtdEmail.style.visibility=='visible')?'hidden':'visible';
		
		var imgEmail = document.getElementById('idFormVisualizarPaineldeControle:idImgEmail');
		imgEmail.style.visibility=(imgEmail.style.visibility=='visible')?'hidden':'visible';
	}
	/*Para utilizar coloque setInterval('blinkIt()',700); no onload da página*/
}

/*Função que desabilita o botão direito do mouse*/
function bloquearBotaoDireitoMouse(){
	if (event.button==2 || event.button==3) {
		oncontextmenu='return false';
    }
}

function bloquearEnter(evt)
{
    var code = null;
    code = ( evt.keyCode ? evt.keyCode : evt.which );
    return ( code == 13 ) ? false : true;
};

function formataMascaraAndBloquearEnter(objeto, funcao, evt)
{
    var ok = bloquearEnter(evt);
    if(ok){
    	return formataMascara(objeto, funcao);
    }
    return ok;
};

var isModalLogout = false;

function submitBrasZip(action) {
	
//	window.open (action, 'newwindow', config='height=800, width=600, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, directories=no, status=no');
	document.forms["idFormCentralSuporte"].target="_blank";										
	document.forms["idFormCentralSuporte"].action=action;
	document.forms["idFormCentralSuporte"].submit();
	
	//history.go(0);
	//document.location.reload();
}

function submitBrasZipLogin(action) {
	
//	window.open (action, 'newwindow', config='height=800, width=600, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, directories=no, status=no');
	document.forms["idFormLogin"].target="_blank";										
	document.forms["idFormLogin"].action=action;
	document.forms["idFormLogin"].submit();
	
	//history.go(0);
	//document.location.reload();
}

//Codigo responsavel por bloquear o F5 no sistema
//usado no <body onkeydown="showDown(event);">
//<BEGIN showDown>
function showDown(evt){
    evt = (evt) ? evt : ((event) ? event : null);
    if (evt){
        if (navigator.appName == "Netscape"){
            if (evt.which == 116){
                // When F5 is pressed
                cancelKey(evt);
            }
            
        }else {
            if (event.keyCode == 116){
                // When F5 is pressed
                cancelKey(evt);
            } 
        }
    }
}

function cancelKey(evt){
    if (evt.preventDefault){
        evt.preventDefault();
        return false;
    }else{
        evt.keyCode = 0;
        evt.returnValue = false;
    }
}
//<END showDown>
