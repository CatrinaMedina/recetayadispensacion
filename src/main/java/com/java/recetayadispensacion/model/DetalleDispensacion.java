
package com.java.recetayadispensacion.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detalle_dispensacion")
public class DetalleDispensacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicamentoId;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "dispensacion_id", nullable = false)
    @JsonIgnore
    private Dispensacion dispensacion;

    public DetalleDispensacion() {
    }

    public DetalleDispensacion(Long medicamentoId, Integer cantidad) {
        this.medicamentoId = medicamentoId;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return this.id;
    }

    public Long getMedicamentoId() {
        return this.medicamentoId;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Dispensacion getDispensacion() {
        return this.dispensacion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setDispensacion(Dispensacion dispensacion) {
        this.dispensacion = dispensacion;
    }
}
