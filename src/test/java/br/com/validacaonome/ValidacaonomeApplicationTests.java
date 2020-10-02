package br.com.validacaonome;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import br.com.validacaonome.dao.BlackListEntity;
import br.com.validacaonome.dao.BlackListRepository;
import br.com.validacaonome.dto.RequestValidacaoNomedto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ValidacaonomeApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Autowired
	private BlackListRepository blackRepository;

	@Before
	public void setup() throws IOException {
		BlackListEntity name1 = new BlackListEntity();
		name1.setName("Simba");

		BlackListEntity name2 = new BlackListEntity();
		name2.setName("Biscoito");

		blackRepository.save(name1);
		blackRepository.save(name2);
	}

	@Test
	public void testarNomeValido() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("AndreCoelho");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Nome Aceito\"}", response.getBody());
		assertTrue(200 == response.getStatusCodeValue());
	}
	
	@Test
	public void testarNomeBlackList() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("Biscoito");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Esse Nome Não é Valido para uso!\"}", response.getBody());
		assertTrue(400 == response.getStatusCodeValue());
	}
	
	@Test
	public void testarNomeCaracterEspecial() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("Andre@Coelho");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Esse Nome Não é Valido para uso!\"}", response.getBody());
		assertTrue(400 == response.getStatusCodeValue());
	}
	
	@Test
	public void testarNomeMaior26() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho Andre Coelho");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Esse Nome Não é Valido para uso!\"}", response.getBody());
		assertTrue(400 == response.getStatusCodeValue());
	}
	
	@Test
	public void testarNomeMenor4() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("An");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Esse Nome Não é Valido para uso!\"}", response.getBody());
		assertTrue(400 == response.getStatusCodeValue());
	}
	
	@Test
	public void testarNomeVazio() throws Exception {
		
		RequestValidacaoNomedto request = new RequestValidacaoNomedto();
		request.setName("");
		Gson gson = new Gson();
		String json = gson.toJson(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/validarname", HttpMethod.POST, entity, String.class);
		
		assertEquals("{\"response\":\"Esse Nome Não é Valido para uso!\"}", response.getBody());
		assertTrue(400 == response.getStatusCodeValue());
	}

}
