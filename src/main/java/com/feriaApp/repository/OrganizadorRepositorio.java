package com.feriaApp.repository;

import com.feriaApp.models.Organizador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizadorRepositorio extends MongoRepository<Organizador, String> {
    Organizador findByCorreo(String correo);
}
