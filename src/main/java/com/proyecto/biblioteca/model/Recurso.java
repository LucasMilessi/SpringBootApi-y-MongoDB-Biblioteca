package com.proyecto.biblioteca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "recursos")
public class Recurso {

    @Id
    private String recursoId = UUID.randomUUID().toString().substring(0, 10);

    private LocalDate fechaDePrestado;
    private boolean isPrestado;
    private String titulo;
    private String tipo;
    private String areaTematica;


    public Recurso(String titulo, String tipo, String areaTematica) {
        this.fechaDePrestado = LocalDate.now();
        this.titulo = titulo;
        this.tipo = tipo;
        this.isPrestado = false;
        this.areaTematica = areaTematica;
    }

    public Recurso setPrestado(boolean prestado) {
        this.isPrestado = prestado;
        return this;
    }

    public String getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(String recursoId) {
        this.recursoId = recursoId;
    }

    public LocalDate getFechaDePrestado() {
        return fechaDePrestado;
    }

    public void setFechaDePrestado(LocalDate fechaDePrestado) {
        this.fechaDePrestado = fechaDePrestado;
    }

    public boolean isPrestado() {
        return isPrestado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

}
