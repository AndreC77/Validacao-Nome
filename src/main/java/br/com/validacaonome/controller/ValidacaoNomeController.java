package br.com.validacaonome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.validacaonome.core.ValidarNome;
import br.com.validacaonome.dto.RequestValidacaoNomedto;
import br.com.validacaonome.dto.ResponseValidacaonomedto;

@RestController
@RequestMapping("/validarname")
@CrossOrigin
public class ValidacaoNomeController {
	
	public static final Logger logger = LoggerFactory.getLogger(ValidacaoNomeController.class);
	
	@Autowired
	private ValidarNome validarNome;
	
	@PostMapping
	public ResponseEntity<?> validarNomeSugerido(@RequestBody RequestValidacaoNomedto nomeSugerido){
		
		ResponseValidacaonomedto resp = new ResponseValidacaonomedto();
		
		if(validarNome.isNomeValido(nomeSugerido.getName())) {
			resp.setResponse("Nome Aceito");
			return new ResponseEntity<>(resp,HttpStatus.OK);
		}else {
			resp.setResponse("Esse Nome Não é Valido para uso!");
			return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
		}
		
	}

}
