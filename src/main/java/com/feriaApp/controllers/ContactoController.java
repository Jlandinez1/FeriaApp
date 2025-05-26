package com.feriaApp.controllers;

import com.feriaApp.models.MensajeContacto;
import com.feriaApp.repository.MensajeContactoRepositorio;
import com.feriaApp.models.PreguntaFrecuente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    private final MensajeContactoRepositorio mensajeRepositorio;
    

    public ContactoController(MensajeContactoRepositorio mensajeRepositorio) {
        this.mensajeRepositorio = mensajeRepositorio;
    }

    // Muestra el formulario de contacto
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new MensajeContacto());
        model.addAttribute("faq", getFAQ()); // Lista de preguntas frecuentes
        return "views/contacto";
    }

    // Procesa el envío del formulario
    @PostMapping("/enviar")
    public String enviarMensaje(@ModelAttribute MensajeContacto mensaje) {
        mensaje.setRespondido(false); // No es necesario que sea true por defecto
        mensajeRepositorio.save(mensaje);
        return "redirect:/contacto?exito";
    }

    // Ejemplo de preguntas frecuentes (simuladas)
    private List<PreguntaFrecuente> getFAQ() {
        return List.of(
            new PreguntaFrecuente("¿Cómo hacer una reserva?", "Ingresa como cliente y selecciona la opción 'Reservar' en cada producto"),
            new PreguntaFrecuente("¿Dónde ver mis reservas?", "En el dashboard del cliente → 'Mis Reservas'")
        );
    }
}