package com.feriaApp.controllers;

import com.feriaApp.models.Feria;
import com.feriaApp.models.Producto;
import com.feriaApp.repository.FeriaRepositorio;
import com.feriaApp.repository.ProductoRepositorio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cliente/publico")
public class ClientePublicoController {

    private final ProductoRepositorio productoRepositorio;
    private final FeriaRepositorio feriaRepositorio;

    public ClientePublicoController(ProductoRepositorio productoRepositorio,
                                    FeriaRepositorio feriaRepositorio) {
        this.productoRepositorio = productoRepositorio;
        this.feriaRepositorio = feriaRepositorio;
    }

    @GetMapping
    public String verVistaPublica(Model model) {

        // Obtener ferias futuras
        LocalDate hoy = LocalDate.now();
        List<Feria> feriasProximas = feriaRepositorio.findAll().stream()
                .filter(f -> f.getFechaInicio() != null && !f.getFechaInicio().isBefore(hoy))
                .sorted((a, b) -> a.getFechaInicio().compareTo(b.getFechaInicio()))
                .toList();

        // Obtener algunos productos (ej: top 5)
        List<Producto> productosDestacados = productoRepositorio.findAll().stream()
                .limit(5)
                .toList();

        model.addAttribute("ferias", feriasProximas);
        model.addAttribute("productos", productosDestacados);

        return "views/cliente/publico";
    }
}