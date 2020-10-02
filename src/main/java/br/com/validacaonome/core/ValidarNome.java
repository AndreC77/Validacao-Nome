package br.com.validacaonome.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.validacaonome.dao.BlackListRepository;

@Service
public class ValidarNome {
	
	@Autowired
	public BlackListRepository banco;
	
	public boolean isNomeValido(String nomeSugerido) {
	
		
		if(nomeSugerido == null ||nomeSugerido.trim().isEmpty()) {
			return false;
		}
		if(nomeSugerido.length() < 4 || nomeSugerido.length() > 26) {
			return false;
		}
		if(!nomeSugerido.matches("^[a-zA A-Z\\s]*$")) {
			return false;
		}
		
		if(banco.conferirBlackList(nomeSugerido) > 0) {
			return false;
		}
		
		return true; 
	}

}
