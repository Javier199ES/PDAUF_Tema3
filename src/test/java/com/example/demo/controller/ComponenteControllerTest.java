package com.example.demo.controller;


import com.example.demo.model.Componente;
import com.example.demo.controller.ComponenteController;
import com.example.demo.repository.ComponenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComponenteControllerTest {

    private ComponenteRepository repository;
    private ComponenteController controller;

    @BeforeEach
    void setUp() {
        repository = mock(ComponenteRepository.class);
        controller = new ComponenteController(repository);
    }

    @Test
    void getById_debeDevolver200YComponenteCuandoExiste() {
        Componente componente = new Componente();
        componente.setId(1L);
        componente.setNombre("RTX 4060");
        componente.setCategoria("GPU");
        componente.setPrecio(new BigDecimal("299.99"));
        componente.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.of(componente));

        ResponseEntity<Componente> response = controller.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("RTX 4060", response.getBody().getNombre());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getById_debeDevolver404CuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Componente> response = controller.getById(99L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(repository, times(1)).findById(99L);
    }
}