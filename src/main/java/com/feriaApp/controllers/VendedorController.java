package com.feriaApp.controllers;

import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.VendedorRepositorio;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorRepositorio vendedorRepositorio;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        return "views/vendedor/registro";
    }

    @PostMapping("/registro")
    public String registrarVendedor(@ModelAttribute Vendedor vendedor) {
        vendedorRepositorio.save(vendedor);
        return "redirect:/vendedor/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        return "views/vendedor/login";
    }
    
    @PostMapping("/login")
    public String loginVendedor(@ModelAttribute Vendedor vendedor, Model model, HttpSession session) {
        Vendedor encontrado = vendedorRepositorio.findByCorreo(vendedor.getCorreo());
        if (encontrado != null && encontrado.getContraseña().equals(vendedor.getContraseña())) {
            // Guardar vendedorId en la sesión
            session.setAttribute("vendedorId", encontrado.getId());
            System.out.println("VendedorId guardado en sesión: " + encontrado.getId());  // Depuración
            return "redirect:/vendedor/dashboard";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "views/vendedor/login";
        }
    }

@GetMapping("/dashboard")
public String mostrarDashboard(HttpSession session, Model model) {
    String vendedorId = (String) session.getAttribute("vendedorId");

    if (vendedorId == null) {
        return "redirect:/vendedor/login";
    }

    Vendedor vendedor = vendedorRepositorio.findById(vendedorId).orElse(null);
    session.setAttribute("vendedorId", vendedor.getId());
    model.addAttribute("vendedor", vendedor);
    return "views/vendedor/dashboard";
}

}
