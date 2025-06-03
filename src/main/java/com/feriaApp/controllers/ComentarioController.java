package com.feriaApp.controllers;

import com.feriaApp.models.Comentario;
import com.feriaApp.repository.ComentarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    // Muestra la vista con todos los comentarios
    @GetMapping
    public String verComentarios(Model model) {
        model.addAttribute("comentarios", comentarioRepositorio.findAll());
        model.addAttribute("comentario", new Comentario());
        return "views/comentarios";
    }

    // Guarda un nuevo comentario
    @PostMapping("/guardar")
    public String guardarComentario(@ModelAttribute Comentario comentario) {
        comentarioRepositorio.save(comentario);
        return "redirect:/comentarios?exito";
    }
}