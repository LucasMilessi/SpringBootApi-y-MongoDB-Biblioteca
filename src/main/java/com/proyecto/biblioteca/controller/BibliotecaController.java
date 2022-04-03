package com.proyecto.biblioteca.controller;

import com.proyecto.biblioteca.service.IBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("/biblioteca")
@RestController
public class BibliotecaController {

    @Autowired
    IBibliotecaService bibliotecaService;
}
