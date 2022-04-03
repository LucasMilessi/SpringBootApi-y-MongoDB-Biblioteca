package com.proyecto.biblioteca.controller;

import com.proyecto.biblioteca.model.Recurso;
import com.proyecto.biblioteca.service.IBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RequestMapping("/biblioteca")
@RestController
public class BibliotecaController {

    @Autowired
    IBibliotecaService bibliotecaService;

    @GetMapping(value = "/allRecursos")
    private Flux<Recurso> allRecursos() {
        return this.bibliotecaService
                .todosLosRecursos();
    }

    @PostMapping(value = "/addRecurso")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<Recurso> addRecurso(@RequestBody Recurso recurso) {
        return this.bibliotecaService.agregarRecurso(recurso);
    }


    @PutMapping(value = "/editarCliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Mono<ResponseEntity<Recurso>> editarCliente(@PathVariable("id") String recursoId, @RequestBody Recurso recurso) {
        return this.bibliotecaService.actualizarRecurso(recursoId, recurso)
                .flatMap(recurso1 -> Mono.just(ResponseEntity.ok(recurso1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/eliminarRecurso/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Mono<ResponseEntity<Recurso>> eliminarRecurso(@PathVariable("id") String recursoId) {

        return this.bibliotecaService.eliminarRecurso(recursoId)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping(value = "/buscarRecursoPorId/{id}")
    private Mono<Recurso> buscarRecursoPorId(@PathVariable("id") String recursoId) {
        return this.bibliotecaService.recursoPorId(recursoId);
    }


    @GetMapping(value = "/consultarDisponibilidad/{id}")
    public Mono<String> consultarDisponibilidad(@PathVariable("id") String recursoId){
        var isDisponible = bibliotecaService.consultarDisponibilidadDeRecurso(recursoId);
        if(isDisponible == null){
            return isDisponible;
        }
        return isDisponible;
    }

    @PutMapping("/prestarRecurso/{id}")
    public Mono prestarRecurso(@PathVariable("id") String recursoId){
        return bibliotecaService.prestarRecursoDisponible(recursoId);
    }

    @GetMapping(value = "recomendarTipoArea/{tipo}/{area}")
    private Flux<Recurso> recomendarTipoArea(@PathVariable("tipo") String tipo, @PathVariable("area") String area) {
        return this.bibliotecaService.recomendarPorTipoyArea(tipo,area);
    }

    @PutMapping("/devolver/{id}")
    public Mono devolverRecurso(@PathVariable("id") String id){
        return bibliotecaService.devolverRecursoPrestado(id);
    }


}
