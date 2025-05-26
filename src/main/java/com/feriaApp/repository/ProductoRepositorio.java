package com.feriaApp.repository;

import com.feriaApp.models.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import java.util.List;

public interface ProductoRepositorio extends MongoRepository<Producto, String> {
    List<Producto> findByVendedorId(String vendedorId);
    
    @Aggregation("{ $group: { _id: '$categoria', count: { $sum: 1 } } }")
    List<String> findAllCategorias();
}

