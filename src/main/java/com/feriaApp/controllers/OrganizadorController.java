package com.feriaApp.controllers;

import com.feriaApp.models.Organizador;
import com.feriaApp.repository.OrganizadorRepositorio;
import com.feriaApp.Services.ReporteService;
import com.feriaApp.models.FeriaConAsistentesDTO;
import com.feriaApp.models.Notificacion;
import com.feriaApp.repository.NotificacionRepositorio;


import jakarta.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/organizador")
public class OrganizadorController {

    @Autowired
    private OrganizadorRepositorio organizadorRepositorio;
    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("organizador", new Organizador());
        return "views/organizador/registro";
    }

    @PostMapping("/registro")
    public String registrarOrganizador(@ModelAttribute Organizador organizador) {
        organizadorRepositorio.save(organizador);
        return "redirect:/organizador/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("organizador", new Organizador());
        return "views/organizador/login";
    }

    @PostMapping("/login")
    public String loginOrganizador(@ModelAttribute Organizador organizador, Model model, HttpSession session) {
        Organizador encontrado = organizadorRepositorio.findByCorreo(organizador.getCorreo());
        if (encontrado != null && encontrado.getIdEmpleado().equals(organizador.getIdEmpleado())
                && encontrado.getContraseña().equals(organizador.getContraseña())) {
            
            session.setAttribute("organizadorLogueado", encontrado); // Guardar en sesión
            return "redirect:/organizador/dashboard";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "views/organizador/login";
        }
    }

    
    @GetMapping("/dashboard")
    public String inicioOrganizador(Model model) {
        Organizador organizador = new Organizador(); // aquí deberías obtener el organizador real
        organizador.setNombre("Organizador"); // reemplázalo con el valor real si lo tienes

        model.addAttribute("organizador", organizador);
        return "views/organizador/dashboard";
    }
    
    @GetMapping("/notificaciones")
    public String verNotificaciones(HttpSession session, Model model) {
        Organizador organizador = (Organizador) session.getAttribute("organizadorLogueado");

        if (organizador == null) {
            return "redirect:/organizador/log"
            		+ "in";
        }

        List<Notificacion> notificaciones = notificacionRepositorio.findByOrganizadorIdAndLeidaFalse(organizador.getId());
        model.addAttribute("notificaciones", notificaciones);

        return "views/organizador/notificaciones";
    }
    
        @Autowired
        private ReporteService reporteService;
    
        @GetMapping("/reportes/asistencias")
        public String verReporteAsistencias(Model model) {
            List<FeriaConAsistentesDTO> reporte = reporteService.getReporteAsistencias();
            model.addAttribute("reporte", reporte);
            return "views/organizador/reportes/asistencias";
        }

}
