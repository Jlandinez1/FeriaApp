package com.feriaApp.repository;

import com.feriaApp.models.Vendedor;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VendedorRepositorio extends MongoRepository<Vendedor, String> {
    Vendedor findByCorreo(String correo);
    List<Vendedor> findByEstado(String estado); // Para filtrar por "Aprobado"

}
