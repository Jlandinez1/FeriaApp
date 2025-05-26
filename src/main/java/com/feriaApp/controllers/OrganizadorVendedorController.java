package com.feriaApp.controllers;

import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.VendedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/organizador/vendedores")
public class OrganizadorVendedorController {

    @Autowired
    private VendedorRepositorio vendedorRepositorio;

    @GetMapping
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorRepositorio.findAll();
        model.addAttribute("vendedores", vendedores);
        return "views/organizador/vendedores";
    }

    @GetMapping("/aprobar/{id}")
    public String aprobarVendedor(@PathVariable String id) {
        Vendedor vendedor = vendedorRepositorio.findById(id).orElse(null);
        if (vendedor != null) {
            vendedor.setEstado("aprobado");
            vendedorRepositorio.save(vendedor);
        }
        return "redirect:/organizador/vendedores";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazarVendedor(@PathVariable String id) {
        Vendedor vendedor = vendedorRepositorio.findById(id).orElse(null);
        if (vendedor != null) {
            vendedor.setEstado("rechazado");
            vendedorRepositorio.save(vendedor);
        }
        return "redirect:/organizador/vendedores";
    }
    
    @GetMapping("/dashboard")
    public String mostrarDashboard() {
        return "views/organizador/dashboard";
    }
}
