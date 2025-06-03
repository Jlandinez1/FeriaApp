package com.feriaApp.controllers;

import com.feriaApp.models.Stand;
import com.feriaApp.repository.StandRepositorio;
import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.VendedorRepositorio;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stands")
public class StandController {

    @Autowired
    private StandRepositorio standRepositorio;
    @Autowired
    private VendedorRepositorio vendedorRepositorio;
    
    @Autowired
    public StandController(VendedorRepositorio vendedorRepositorio, StandRepositorio standRepositorio) {
        this.vendedorRepositorio = vendedorRepositorio;
        this.standRepositorio = standRepositorio;
    }

    // Mostrar todos los stands
    @GetMapping
    public String listarStands(Model model) {
        model.addAttribute("stands", standRepositorio.findAll());
        return "views/stands/lista";
    }

    // Mostrar formulario para crear nuevo stand
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("stand", new Stand());
        return "views/stands/formulario";
    }

    // Guardar stand
    @PostMapping("/guardar")
    public String guardarStand(@ModelAttribute Stand stand) {
        standRepositorio.save(stand);
        return "redirect:/stands";
    }
    
    @GetMapping("/editar/{id}")
    public String editarStand(@PathVariable String id, Model model) {
        Stand stand = standRepositorio.findById(id).orElse(null);
        if (stand == null) {
            return "redirect:/stands";
        }
        model.addAttribute("stand", stand);
        return "views/stands/formulario";
    }

    // Eliminar un stand
    @GetMapping("/eliminar/{id}")
    public String eliminarStand(@PathVariable String id) {
        standRepositorio.deleteById(id);
        return "redirect:/stands";
    }
        @GetMapping("/asignarStand")
        public String mostrarAsignacion(Model model) {
            List<Stand> standsDisponibles = standRepositorio.findByDisponible(true);
            List<Vendedor> vendedoresAprobados = vendedorRepositorio.findByEstado("aprobado");

            model.addAttribute("stands", standsDisponibles);
            model.addAttribute("vendedores", vendedoresAprobados);
            return "views/stands/asignarStand";
        }

        @PostMapping("/asignar")
        public String asignarStand(@RequestParam String standId, @RequestParam String vendedorId) {
            Optional<Stand> optionalStand = standRepositorio.findById(standId);
            if (optionalStand.isPresent()) {
                Stand stand = optionalStand.get();
                stand.setDisponible(false);
                stand.setIdVendedor(vendedorId);
                standRepositorio.save(stand);
            }
            return "redirect:/stands";
        }
	}
