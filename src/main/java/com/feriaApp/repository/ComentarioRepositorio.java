package com.feriaApp.repository;

import com.feriaApp.models.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComentarioRepositorio extends MongoRepository<Comentario, String> {
    List<Comentario> findAllByOrderByFechaDesc(); // O similar si usas campo de fecha
}