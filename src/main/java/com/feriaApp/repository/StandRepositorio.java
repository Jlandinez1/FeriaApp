package com.feriaApp.repository;

import com.feriaApp.models.Stand;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StandRepositorio extends MongoRepository<Stand, String> {
	List<Stand> findByDisponible(boolean disponible);

}
