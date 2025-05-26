package com.feriaApp.controllers;

import com.feriaApp.models.Feria;
import com.feriaApp.repository.FeriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/organizador/ferias")
public class FeriaController {

    @Autowired
    private FeriaRepositorio feriaRepositorio;

    @GetMapping
    public String listarFerias(Model model) {
        model.addAttribute("ferias", feriaRepositorio.findAll());
        return "views/organizador/ferias/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("feria", new Feria());
        return "views/organizador/ferias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarFeria(@ModelAttribute Feria feria) {
        if (feria.getId() == null || feria.getId().isEmpty()) {
            feria.setId(UUID.randomUUID().toString());
        }
        feriaRepositorio.save(feria);
        return "redirect:/organizador/ferias";
    }

    @GetMapping("/editar/{id}")
    public String editarFeria(@PathVariable String id, Model model) {
        Feria feria = feriaRepositorio.findById(id).orElse(null);
        if (feria != null) {
            model.addAttribute("feria", feria);
            return "views/organizador/ferias/formulario";
        }
        return "redirect:/organizador/ferias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFeria(@PathVariable String id) {
        feriaRepositorio.deleteById(id);
        return "redirect:/organizador/ferias";
    }
}
