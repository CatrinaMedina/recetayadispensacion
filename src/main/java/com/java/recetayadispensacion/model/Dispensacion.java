
package com.java.recetayadispensacion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dispensaciones")
public class Dispensacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long recetaId;

    private Long farmaceuticoId;
    private Long sucursalId;
    private LocalDateTime fecha;
    private String estado;

    @OneToMany(
        mappedBy = "dispensacion",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<DetalleDispensacion> detalles = new ArrayList<>();

    public Dispensacion() {
    }

    public Dispensacion(Long recetaId, Long farmaceuticoId,
                        Long sucursalId) {
        this.recetaId = recetaId;
        this.farmaceuticoId = farmaceuticoId;
        this.sucursalId = sucursalId;
        this.fecha = LocalDateTime.now();
        this.estado = "PENDIENTE_CONFIRMACION";
    }

    public Long getId() {
        return this.id;
    }

    public Long getRecetaId() {
        return this.recetaId;
    }

    public Long getFarmaceuticoId() {
        return this.farmaceuticoId;
    }

    public Long getSucursalId() {
        return this.sucursalId;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public String getEstado() {
        return this.estado;
    }

    public List<DetalleDispensacion> getDetalles() {
        return this.detalles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public void setFarmaceuticoId(Long farmaceuticoId) {
        this.farmaceuticoId = farmaceuticoId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDetalles(List<DetalleDispensacion> detalles) {

        this.detalles.clear();

        if (detalles != null) {
            for (DetalleDispensacion detalle : detalles) {
                detalle.setDispensacion(this);
                this.detalles.add(detalle);
            }
        }
    }
}
