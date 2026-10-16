
package com.java.recetayadispensacion.repository;

import com.java.recetayadispensacion.model.Dispensacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispensacionRepository
        extends JpaRepository<Dispensacion, Long> {

    Optional<Dispensacion> findByRecetaId(Long recetaId);

}
