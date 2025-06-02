package com.feriaApp.repository;

import com.feriaApp.models.Asistencia;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface AsistenciaRepositorio extends MongoRepository<Asistencia, String> {
    List<Asistencia> findByClienteId(String clienteId);
    Asistencia findByClienteIdAndFeriaId(String clienteId, String feriaId);

    // Si quieres por feria y cliente, para saber si un cliente confirmó asistencia
    Optional<Asistencia> findByFeriaIdAndClienteIdAndConfirmada(String feriaId, String clienteId, boolean confirmada);

    List<Asistencia> findByFeriaIdAndConfirmada(String feriaId, boolean confirmada);
}