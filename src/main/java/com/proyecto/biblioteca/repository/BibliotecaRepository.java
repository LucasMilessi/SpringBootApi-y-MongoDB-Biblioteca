package com.proyecto.biblioteca.repository;

import com.proyecto.biblioteca.model.Recurso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecaRepository extends ReactiveMongoRepository<Recurso, String> {
}
