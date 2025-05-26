package com.feriaApp.repository;

import com.feriaApp.models.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReservaRepositorio extends MongoRepository<Reserva, String> {
    List<Reserva> findByClienteId(String clienteId);
}