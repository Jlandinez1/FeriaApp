package com.feriaApp.controllers;

import com.feriaApp.models.Cliente;
import com.feriaApp.models.Producto;
import com.feriaApp.models.Reserva;
import com.feriaApp.models.ReservaReporteDTO;
import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.VendedorRepositorio;
import com.feriaApp.repository.ClienteRepositorio;
import com.feriaApp.repository.ProductoRepositorio;
import com.feriaApp.repository.ReservaRepositorio;


import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorRepositorio vendedorRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private ProductoRepositorio productoRepositorio;

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

@GetMapping("/reservas/reporte")
public String verReporteReservas(HttpSession session, Model model){
String vendedorId = (String) session.getAttribute("vendedorId");
    if (vendedorId == null) return "redirect:/vendedor/login";

    List<Producto> productos = productoRepositorio.findByVendedorId(vendedorId);

    List<ReservaReporteDTO> reporte = new ArrayList<>();

    for (Producto p : productos) {
        List<Reserva> reservas = reservaRepositorio.findByProductoId(p.getId());

        if (!reservas.isEmpty()) {
            long totalReservas = reservas.size();
            int cantidadTotal = reservas.stream().mapToInt(Reserva::getCantidad).sum();
            LocalDate ultimaReserva = reservas.stream()
                    .map(Reserva::getFechaReserva)
                    .max(LocalDate::compareTo)
                    .orElse(null);

            for (Reserva r : reservas) {
                Cliente cliente = clienteRepositorio.findById(r.getClienteId()).orElse(null);
                reporte.add(new ReservaReporteDTO(
                        p.getNombre(),
                        cliente != null ? cliente.getNombre() : "Desconocido",
                        cantidadTotal,
                        ultimaReserva,
                        totalReservas
                ));
            }
        } else {
            reporte.add(new ReservaReporteDTO(p.getNombre(), "Ninguno", 0, null, 0));
        }
    }

    model.addAttribute("reporte", reporte);
    return "views/vendedor/reservas/reporte_reservas";
}

}
