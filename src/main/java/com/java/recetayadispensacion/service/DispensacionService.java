package com.java.recetayadispensacion.service;

import com.java.recetayadispensacion.model.Dispensacion;

import java.util.List;
import java.util.Optional;

public interface DispensacionService {

    Dispensacion guardar(Dispensacion dispensacion);

    List<Dispensacion> obtenerTodos();

    Optional<Dispensacion> buscaPorId(Long id);

    Optional<Dispensacion> buscarPorReceta(Long recetaId);

    Optional<Dispensacion> actualizar(Long id, Dispensacion datos);

    boolean eliminar(Long id);
}