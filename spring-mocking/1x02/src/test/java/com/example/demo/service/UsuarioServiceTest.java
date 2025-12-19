package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        // Arrange
        Usuario usuario = new Usuario(1L, "Maria", "maria@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        // Arrange
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.buscarUsuarioPorId(99L));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        // Arrange
        Usuario usuario = new Usuario(null, "João", "joao@email.com");
        Usuario usuarioSalvo = new Usuario(1L, "João", "joao@email.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = usuarioService.salvarUsuario(usuario);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("João", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
    }
}

