package com.feriaApp.repository;

import com.feriaApp.models.Feria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeriaRepositorio extends MongoRepository<Feria, String> {
}
