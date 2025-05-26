package com.feriaApp.repository;

import com.feriaApp.models.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificacionRepositorio extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByOrganizadorIdAndLeidaFalse(String organizadorId);
}