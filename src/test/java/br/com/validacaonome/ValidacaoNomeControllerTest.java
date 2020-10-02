package br.com.validacaonome;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.validacaonome.controller.ValidacaoNomeController;
import br.com.validacaonome.core.ValidarNome;
import br.com.validacaonome.dto.RequestValidacaoNomedto;
import br.com.validacaonome.dto.ResponseValidacaonomedto;

@RunWith(MockitoJUnitRunner.class)
public class ValidacaoNomeControllerTest {
	
	
	
	@InjectMocks
	ValidacaoNomeController controller = new ValidacaoNomeController();
	
	@Mock
	ValidarNome validar = new ValidarNome();
	
	
	@Test
	public void testeControllerSucess() {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("Andre Coelho");
		Mockito.when(validar.isNomeValido("Andre Coelho")).thenReturn(true);
		
		ResponseEntity<?> response = controller.validarNomeSugerido(request);
		ResponseValidacaonomedto body = (ResponseValidacaonomedto) response.getBody();
		Assert.assertEquals("Nome Aceito", body.getResponse());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testeControllerFail() {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("André @@#");
		Mockito.when(validar.isNomeValido("André @@#")).thenReturn(false);
		
		ResponseEntity<?> response = controller.validarNomeSugerido(request);
		ResponseValidacaonomedto body = (ResponseValidacaonomedto) response.getBody();
		Assert.assertEquals("Esse Nome Não é Valido para uso!", body.getResponse());
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}

}
