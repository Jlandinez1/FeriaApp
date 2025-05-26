package com.feriaApp.controllers;
import com.feriaApp.models.Feria;
import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.FeriaRepositorio;
import com.feriaApp.repository.VendedorRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class HomeController {

@Autowired
private FeriaRepositorio feriaRepositorio;

@Autowired
private VendedorRepositorio vendedorRepositorio;

    @GetMapping("/")
    public String mostrarHome(Model model) {
        // Obtener ferias próximas
        LocalDate hoy = LocalDate.now();
        List<Feria> feriasProximas = feriaRepositorio.findAll().stream()
                .filter(f -> f.getFechaInicio() != null && !f.getFechaInicio().isBefore(hoy))
                .sorted(Comparator.comparing(Feria::getFechaInicio))
                .toList();

        // Obtener vendedores (todos o algunos destacados)
        List<Vendedor> vendedoresDestacados = vendedorRepositorio.findAll().stream()
                .limit(5)
                .toList();

        model.addAttribute("ferias", feriasProximas);
        model.addAttribute("vendedores", vendedoresDestacados);

        return "home";
    }

    @GetMapping("/terminos")
    public String mostrarTerminos() {
        return "views/terminos";
    }
}
