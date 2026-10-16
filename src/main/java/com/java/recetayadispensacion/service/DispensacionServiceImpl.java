package com.java.recetayadispensacion.service;

import com.java.recetayadispensacion.model.Dispensacion;
import com.java.recetayadispensacion.model.DetalleDispensacion;
import com.java.recetayadispensacion.repository.DispensacionRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DispensacionServiceImpl implements DispensacionService {

    private final DispensacionRepository repository;

    public DispensacionServiceImpl(DispensacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dispensacion guardar(Dispensacion dispensacion) {

        validar(dispensacion);

        if (repository.findByRecetaId(dispensacion.getRecetaId()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Ya existe una dispensacion para esta receta");
        }

        dispensacion.setId(null);
        dispensacion.setFecha(LocalDateTime.now());
        dispensacion.setEstado("PENDIENTE_CONFIRMACION");

        for (DetalleDispensacion detalle : dispensacion.getDetalles()) {
            detalle.setId(null);
            detalle.setDispensacion(dispensacion);
        }

        return repository.save(dispensacion);
    }

    @Override
    public List<Dispensacion> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Dispensacion> buscaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Dispensacion> buscarPorReceta(Long recetaId) {
        return repository.findByRecetaId(recetaId);
    }

    @Override
    public Optional<Dispensacion> actualizar(Long id, Dispensacion datos) {

        validar(datos);

        return repository.findById(id)
            .map(dispensacion -> {

                if (!dispensacion.getEstado().equals("PENDIENTE_CONFIRMACION")) {
                    throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "La dispensacion no se puede modificar");
                }

                if (!dispensacion.getRecetaId().equals(datos.getRecetaId())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se puede cambiar la receta asociada");
                }

                dispensacion.setFarmaceuticoId(datos.getFarmaceuticoId());
                dispensacion.setSucursalId(datos.getSucursalId());

                List<DetalleDispensacion> nuevosDetalles = new ArrayList<>();

                for (DetalleDispensacion detalle : datos.getDetalles()) {

                    DetalleDispensacion nuevo = new DetalleDispensacion();
                    nuevo.setMedicamentoId(detalle.getMedicamentoId());
                    nuevo.setCantidad(detalle.getCantidad());
                    nuevosDetalles.add(nuevo);
                }

                dispensacion.setDetalles(nuevosDetalles);

                return repository.save(dispensacion);
            });
    }

    @Override
    public boolean eliminar(Long id) {

        Optional<Dispensacion> encontrado = repository.findById(id);

        if (encontrado.isEmpty()) {
            return false;
        }

        if (!encontrado.get().getEstado().equals("PENDIENTE_CONFIRMACION")) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No se puede eliminar una dispensacion confirmada");
        }

        repository.deleteById(id);
        return true;
    }

    private void validar(Dispensacion dispensacion) {

        if (dispensacion == null
                || dispensacion.getRecetaId() == null
                || dispensacion.getRecetaId() <= 0
                || dispensacion.getFarmaceuticoId() == null
                || dispensacion.getFarmaceuticoId() <= 0
                || dispensacion.getSucursalId() == null
                || dispensacion.getSucursalId() <= 0
                || dispensacion.getDetalles() == null
                || dispensacion.getDetalles().isEmpty()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Datos de dispensacion incompletos");
        }

        for (DetalleDispensacion detalle : dispensacion.getDetalles()) {

            if (detalle == null
                    || detalle.getMedicamentoId() == null
                    || detalle.getMedicamentoId() <= 0
                    || detalle.getCantidad() == null
                    || detalle.getCantidad() <= 0) {

                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los medicamentos y cantidades deben ser validos");
            }
        }
    }
}