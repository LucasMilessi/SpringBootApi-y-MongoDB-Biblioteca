package com.proyecto.biblioteca.service;

import com.proyecto.biblioteca.model.Recurso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBibliotecaService {

    Flux<Recurso> todosLosRecursos();

    Mono<Recurso> agregarRecurso(Recurso recurso);

    Mono<Recurso> actualizarRecurso(String recursoId, Recurso recurso);

    Mono<Recurso> eliminarRecurso(String recursoId);

    Mono<Recurso> recursoPorId(String recursoId);

    Mono<String> consultarDisponibilidadDeRecurso(String recursoId);

    Mono<String> validarDisponibilidadDeRecurso(Mono<Recurso> recurso);

    Mono<String> prestarRecursoDisponible(String recursoId);

    Flux<Recurso> recomendarPorTipoyArea(String tipo, String area);

    Mono<Object> devolverRecursoPrestado(String recursoId);
}
