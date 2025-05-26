package com.feriaApp.repository;

import com.feriaApp.models.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepositorio extends MongoRepository<Cliente, String> {
    Cliente findByCorreo(String correo);
}
