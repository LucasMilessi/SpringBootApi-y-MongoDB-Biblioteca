package com.proyecto.biblioteca.service;

import com.proyecto.biblioteca.model.Recurso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBibliotecaService {

    Flux<Recurso> todosLosRecursos();

    Mono<Recurso> agregarRecurso(Recurso recurso);

    Mono<Recurso> actualizarRecurso(String recursoId, Recurso recurso);

    Mono<Recurso> eliminarRecurso(String recursoId);
}
