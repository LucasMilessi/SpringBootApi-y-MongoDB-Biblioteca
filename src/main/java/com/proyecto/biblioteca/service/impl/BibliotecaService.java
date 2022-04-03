package com.proyecto.biblioteca.service.impl;

import com.proyecto.biblioteca.model.Recurso;
import com.proyecto.biblioteca.repository.BibliotecaRepository;
import com.proyecto.biblioteca.service.IBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

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

    @Override
    public Mono<Recurso> eliminarRecurso(String recursoId){
        return bibliotecaRepository
                .findById(recursoId)
                .flatMap(recurso -> bibliotecaRepository
                        .deleteById(recurso.getRecursoId()).thenReturn(recurso));
    }

    @Override
    public Mono<Recurso> recursoPorId(String recursoId) {

        return this.bibliotecaRepository
                .findById(recursoId);
    }

    @Override
    public Mono<String> consultarDisponibilidadDeRecurso(String recursoId) {

        Mono<Recurso> recurso = bibliotecaRepository.findById(recursoId);
        return validarDisponibilidadDeRecurso(recurso);
    }

    @Override
    public Mono<String> validarDisponibilidadDeRecurso(Mono<Recurso> recurso) {
        try {
            return recurso.map(recurso1 -> {
                        var disponibilidad = recurso1
                                .isPrestado() ? "El recurso no se encurntra disponible desde la fecha: " + recurso1.getFechaDePrestado()
                                : "El recurso esta disponible";

                        return Mono.just(disponibilidad);

                    }).toFuture()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Mono<String> prestarRecursoDisponible(String recursoId) {

        return bibliotecaRepository
                .findById(recursoId)
                .flatMap(recurso -> {
                            if(recurso.isPrestado()){
                                return Mono.just("El recurso no esta disponible porque ya fue prestado en la fecha : "+ recurso.getFechaDePrestado());
                            }
                            recurso.setPrestado(true);
                            recurso.setFechaDePrestado(LocalDate.now());

                            return bibliotecaRepository
                                    .save(recurso)
                                    .then(Mono.just("Recurso prestado"));
                        }
                );
    }

    @Override
    public Flux<Recurso> recomendarPorTipoyArea(String tipo, String area) {

        return bibliotecaRepository
                .findByTipo(tipo)
                .filter(recurso -> recurso.getAreaTematica()
                        .equals(area));
    }

    @Override
    public Mono<Object> devolverRecursoPrestado(String recursoId) {

        return bibliotecaRepository
                .findById(recursoId)
                .flatMap(recurso-> {

                    if(!recurso.isPrestado() ) {
                        return Mono.just("El recurso ya fue devuelto, muchas gracias");

                    }else{
                        recurso.setPrestado(false);
                        return bibliotecaRepository
                                .save(recurso);
                    }
                } );
    }
}
