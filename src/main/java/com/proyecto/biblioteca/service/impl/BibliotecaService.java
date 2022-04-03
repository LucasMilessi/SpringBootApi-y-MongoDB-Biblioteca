package com.proyecto.biblioteca.service.impl;

import com.proyecto.biblioteca.model.Recurso;
import com.proyecto.biblioteca.repository.BibliotecaRepository;
import com.proyecto.biblioteca.service.IBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BibliotecaService implements IBibliotecaService {

    @Autowired
    BibliotecaRepository bibliotecaRepository;

    @Override
    public Flux<Recurso> todosLosRecursos() {

        return this.bibliotecaRepository
                .findAll();
    }

    @Override
    public Mono<Recurso> agregarRecurso(Recurso recurso) {

        return this.bibliotecaRepository
                .save(recurso);
    }

    @Override
    public Mono<Recurso> actualizarRecurso(String recursoId, Recurso recurso) {

        return this.bibliotecaRepository.findById(recursoId)
                .flatMap(recurso1 -> {
                    recurso.setRecursoId(recursoId);
                    return agregarRecurso(recurso);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Recurso> eliminarRecurso(String recursoId){
        return bibliotecaRepository
                .findById(recursoId)
                .flatMap(recurso -> bibliotecaRepository
                        .deleteById(recurso.getRecursoId()).thenReturn(recurso));
    }
}
