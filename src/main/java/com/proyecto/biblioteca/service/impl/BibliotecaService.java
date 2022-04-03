package com.proyecto.biblioteca.service.impl;

import com.proyecto.biblioteca.repository.BibliotecaRepository;
import com.proyecto.biblioteca.service.IBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService implements IBibliotecaService {

    @Autowired
    BibliotecaRepository bibliotecaRepository;
}
