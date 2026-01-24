package com.example.demo.controller;

import com.example.demo.model.Componente;
import com.example.demo.repository.ComponenteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/componentes")
public class ComponenteController {

    private final ComponenteRepository repository;

    public ComponenteController(ComponenteRepository repository) {
        this.repository = repository;
    }

    // GET todos
    @GetMapping
    public List<Componente> getAll() {
        return repository.findAll();
    }

    // GET por id
    @GetMapping("/{id}")
    public ResponseEntity<Componente> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // POST crear
    @PostMapping
    public ResponseEntity<Componente> create(@Valid @RequestBody Componente componente) {
        Componente guardado = repository.save(componente);
        return ResponseEntity.ok(guardado);
    }

 // PUT actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Componente> update(
            @PathVariable Long id,
            @RequestBody Componente datosNuevos) {

        return repository.findById(id)
                .map(existente -> {

                    existente.setNombre(datosNuevos.getNombre());
                    existente.setCategoria(datosNuevos.getCategoria());
                    existente.setPrecio(datosNuevos.getPrecio());
                    existente.setStock(datosNuevos.getStock());

                    repository.save(existente);

                    return ResponseEntity.ok(existente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

 // DELETE eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // 1. Si NO existe, devolvemos 404
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // 2. Si existe, lo borramos
        repository.deleteById(id);

        // 3. Devolvemos 204 No Content
        return ResponseEntity.noContent().build();
    }
}
