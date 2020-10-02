package br.com.validacaonome;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.validacaonome.core.ValidarNome;
import br.com.validacaonome.dao.BlackListRepository;

@RunWith(MockitoJUnitRunner.class)
public class ValidarNomeTest {
	
	@InjectMocks
	ValidarNome validar = new ValidarNome();
	
	@Mock
	BlackListRepository blackListRepository;
	
//	@Before
//	public void setUp() throws IOException {
//	    MockitoAnnotations.initMocks(this);
//	}
	
	@Test
	public void nomeValido() {
		Assert.assertEquals(true,validar.isNomeValido("Andre"));
	}
	
	@Test
	public void nomeInvalidoAcentos() {
		Assert.assertEquals(false,validar.isNomeValido("André Coelho"));
	}
	
	@Test
	public void nomeInvalidoCaracteresEspeciais() {
		Assert.assertEquals(false,validar.isNomeValido("@#$%¨&*()_+[]{}:?^`"));
	}
	
	@Test
	public void nomeInvalidoMenor4() {
		Assert.assertEquals(false,validar.isNomeValido("An"));
	}
	
	@Test
	public void nomeInvalidoMaior26() {
		Assert.assertEquals(false,validar.isNomeValido("Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho"));
	}
	
	@Test
	public void nomeInvalidoVazio() {
		Assert.assertEquals(false,validar.isNomeValido(""));
	}
	
	@Test
	public void nomeInvalidoNull() {
		Assert.assertEquals(false,validar.isNomeValido(null));
	}
	
	@Test
	public void nomeInvalidoEspacos() {
		Assert.assertEquals(false,validar.isNomeValido("     "));
	}
	
	@Test
	public void nomeBlackList() {
		Mockito.when(blackListRepository.conferirBlackList("Simba")).thenReturn(5L);
		Assert.assertEquals(false,validar.isNomeValido("Simba"));
	}


}
