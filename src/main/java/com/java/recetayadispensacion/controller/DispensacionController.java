
package com.java.recetayadispensacion.controller;

import com.java.recetayadispensacion.model.Dispensacion;
import com.java.recetayadispensacion.service.DispensacionService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispensaciones")
public class DispensacionController {

    private final DispensacionService dispensacionService;

    public DispensacionController(
            DispensacionService dispensacionService) {

        this.dispensacionService = dispensacionService;
    }

    @PostMapping
    public ResponseEntity<Dispensacion> guardar(
            @RequestBody Dispensacion dispensacion) {

        Dispensacion nuevaDispensacion =
            dispensacionService.guardar(dispensacion);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaDispensacion);
    }

    @GetMapping
    public List<Dispensacion> obtenerTodos() {
        return dispensacionService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispensacion> buscaPorId(
            @PathVariable Long id) {

        return dispensacionService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<Dispensacion> buscarPorReceta(
            @PathVariable Long recetaId) {

        return dispensacionService.buscarPorReceta(recetaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispensacion> actualizar(
            @PathVariable Long id,
            @RequestBody Dispensacion dispensacion) {

        return dispensacionService.actualizar(id, dispensacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (dispensacionService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
