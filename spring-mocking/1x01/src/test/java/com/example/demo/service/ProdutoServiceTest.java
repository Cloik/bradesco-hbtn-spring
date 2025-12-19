package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        // Arrange (cenário)
        Produto produto = new Produto(1L, "Notebook", 3500.0);
        Mockito.when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        // Act (ação)
        Produto resultado = produtoService.buscarPorId(1L);

        // Assert (verificação)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Notebook", resultado.getNome());
        assertEquals(3500.0, resultado.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        // Arrange
        Mockito.when(produtoRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> produtoService.buscarPorId(99L));

        assertEquals("Produto não encontrado", exception.getMessage());
    }
}
