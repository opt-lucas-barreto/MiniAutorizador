package com.vr.miniautorizador;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.vr.miniautorizador.dao.CartaoDAO;
import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.exception.CartaoJaExistenteException;
import com.vr.miniautorizador.service.impl.CartaoServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MiniAutorizadorApplicationTests {

	private CartaoDTO cartaoDTO;

	@Mock
	private CartaoDAO cartaoDAO;

	@InjectMocks
	private CartaoServiceImpl cartaoService; 

	@BeforeEach
	public void setup() {
		this.cartaoDAO = mock(CartaoDAO.class);
		cartaoService = new CartaoServiceImpl(this.cartaoDAO);
		MockitoAnnotations.openMocks(this);//initMocks(this);
	}

	@Test
	void deveInsertirCartao() {
		cartaoDTO = new CartaoDTO("1234", "1234123412341234", null);
		try {
			Assertions.assertEquals(cartaoService.insereCartao(cartaoDTO),cartaoDTO);
		} catch (CartaoJaExistenteException e) {
			e.printStackTrace();
		}

	}

	// @Test
	// void naoDeveInserirCartao(){
	// 	cartaoDTO = new CartaoDTO("1234", "1234123412341234", null);
	// 	try {
	// 		Assertions.assertEquals(cartaoService.insereCartao(cartaoDTO),cartaoDTO);
	// 	} catch (CartaoJaExistenteException e) {
	// 		e.printStackTrace();
	// 	}
	// 	cartaoDTO = new CartaoDTO("1234", "1234123412341234", null);
	// 	Exception exception = assertThrows(CartaoJaExistenteException.class, () -> {
	// 		cartaoService.insereCartao(cartaoDTO);
	// 	});
	
	// 	String mensageEsperada = "For input string";
	// 	String mensagemRecebida = exception.getMessage();
	
	// 	Assertions.assertTrue(mensagemRecebida.contains(mensageEsperada));
	// }

}
