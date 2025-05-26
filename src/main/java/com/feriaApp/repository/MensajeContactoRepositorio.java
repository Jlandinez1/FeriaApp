package com.feriaApp.repository;

import com.feriaApp.models.MensajeContacto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MensajeContactoRepositorio extends MongoRepository<MensajeContacto, String> {
    List<MensajeContacto> findByRespondido(boolean respondido);
}