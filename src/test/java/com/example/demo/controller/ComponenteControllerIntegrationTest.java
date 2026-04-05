package com.example.demo.controller;

import com.example.demo.model.Componente;
import com.example.demo.repository.ComponenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ComponenteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComponenteRepository repository;

    @BeforeEach
    void limpiarBaseDeDatos() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser
    void create_debeGuardarComponenteYDevolver200() throws Exception {
        String json = """
            {
              "nombre": "SSD 1TB",
              "categoria": "Almacenamiento",
              "precio": 89.99,
              "stock": 20
            }
            """;

        mockMvc.perform(post("/api/componentes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("SSD 1TB"))
                .andExpect(jsonPath("$.categoria").value("Almacenamiento"))
                .andExpect(jsonPath("$.precio").value(89.99))
                .andExpect(jsonPath("$.stock").value(20));
    }

    @Test
    @WithMockUser
    void getAll_debeDevolverListaConComponentes() throws Exception {
        Componente c1 = new Componente();
        c1.setNombre("RTX 4060");
        c1.setCategoria("GPU");
        c1.setPrecio(new BigDecimal("299.99"));
        c1.setStock(10);

        Componente c2 = new Componente();
        c2.setNombre("Ryzen 7");
        c2.setCategoria("CPU");
        c2.setPrecio(new BigDecimal("249.99"));
        c2.setStock(5);

        repository.save(c1);
        repository.save(c2);

        mockMvc.perform(get("/api/componentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre").value("RTX 4060"))
                .andExpect(jsonPath("$[1].nombre").value("Ryzen 7"));
    }

    @Test
    @WithMockUser
    void delete_debeEliminarComponenteYDevolver204() throws Exception {
        Componente c = new Componente();
        c.setNombre("RTX 4060");
        c.setCategoria("GPU");
        c.setPrecio(new BigDecimal("299.99"));
        c.setStock(10);

        Componente guardado = repository.save(c);

        mockMvc.perform(delete("/api/componentes/" + guardado.getId()))
                .andExpect(status().isNoContent());
    }
    
    @Test
    @WithMockUser
    void create_debeDevolver400CuandoNombreEstaVacio() throws Exception {
        String json = """
            {
              "nombre": "",
              "categoria": "GPU",
              "precio": 299.99,
              "stock": 10
            }
            """;

        mockMvc.perform(post("/api/componentes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser
    void create_debeDevolver400CuandoPrecioEsNulo() throws Exception {
        String json = """
            {
              "nombre": "RTX 4060",
              "categoria": "GPU",
              "precio": null,
              "stock": 10
            }
            """;

        mockMvc.perform(post("/api/componentes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser
    void create_debeDevolver400CuandoStockEsNegativo() throws Exception {
        String json = """
            {
              "nombre": "RTX 4060",
              "categoria": "GPU",
              "precio": 299.99,
              "stock": -5
            }
            """;

        mockMvc.perform(post("/api/componentes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}