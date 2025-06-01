package com.feriaApp.repository;

import com.feriaApp.models.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepositorio extends MongoRepository<Reserva, String> {
    List<Reserva> findByProductoId(String productoId);
    List<Reserva> findByClienteId(String clienteId);
    List<Reserva> findByFechaReserva(LocalDate fecha);
}