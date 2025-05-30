package com.feriaApp.controllers;

import com.feriaApp.models.Cliente;
import com.feriaApp.repository.ClienteRepositorio;
import com.feriaApp.models.Producto;
import com.feriaApp.repository.ProductoRepositorio;
import com.feriaApp.models.Feria;
import com.feriaApp.repository.FeriaRepositorio;
import com.feriaApp.models.ProductoConVendedorDTO;
import com.feriaApp.models.Vendedor;
import com.feriaApp.repository.VendedorRepositorio;
import com.feriaApp.models.Reserva;
import com.feriaApp.repository.ReservaRepositorio;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.ZoneId;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	public static Date toDate(LocalDate date) {
	    return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private FeriaRepositorio feriaRepositorio;
    @Autowired
    private VendedorRepositorio vendedorRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    


    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "views/cliente/registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute Cliente cliente) {
        clienteRepositorio.save(cliente);
        return "redirect:/cliente/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "views/cliente/login";
    }

    @PostMapping("/login")
    public String loginCliente(@ModelAttribute Cliente cliente, Model model, HttpSession session) {
        model.addAttribute("cliente", new Cliente());
        Cliente encontrado = clienteRepositorio.findByCorreo(cliente.getCorreo());
        if (encontrado != null && encontrado.getContraseña().equals(cliente.getContraseña())) {
            session.setAttribute("clienteId", encontrado.getId()); // ✅ Guarda el ID en sesión
            return "redirect:/cliente/dashboard";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "views/cliente/login";
        }
    }

    @GetMapping("/productos")
    public String verProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "false") boolean popularidad,
            Model model) {

        List<Producto> productos = productoRepositorio.findAll();

        if (categoria != null && !categoria.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                    .toList();
        }

        if (keyword != null && !keyword.isEmpty()) {
            String finalKeyword = keyword.toLowerCase();
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(finalKeyword) ||
                                (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(finalKeyword)))
                    .toList();
        }

        if (popularidad) {
            productos = productos.stream()
                    .sorted(Comparator.comparingInt(Producto::getStock).reversed())
                    .toList();
        }

        List<ProductoConVendedorDTO> productosConVendedor = productos.stream()
                .map(p -> {
                    Vendedor vendedor = vendedorRepositorio.findById(p.getVendedorId()).orElse(null);
                    return new ProductoConVendedorDTO(
                            p.getId(),
                            p.getNombre(),
                            p.getPrecio(),
                            p.getDescripcion(),
                            p.getStock(),
                            p.getCategoria(),
                            p.getImagenUrl(),
                            vendedor != null ? vendedor.getNombre() + " " + vendedor.getApellidos() : "Desconocido"
                    );
                })
                .collect(Collectors.toList());

        model.addAttribute("productos", productosConVendedor);
        model.addAttribute("categorias", productoRepositorio.findAllCategorias());

        return "views/cliente/cliente_productos";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, HttpSession session) {
    	Object clienteId = session.getAttribute("clienteId");

        if (clienteId == null) {
            System.out.println("❌ Sesión sin clienteId");
            model.addAttribute("cliente", new Cliente()); // Aseguramos el atributo
            return "views/cliente/login";
        }
    	
        List<Feria> todasLasFerias = feriaRepositorio.findAll();

        // Obtener hoy con LocalDate
        LocalDate hoy = LocalDate.now();

        // Filtrar ferias próximas (con fechaInicio >= hoy)
        List<Feria> feriasProximas = todasLasFerias.stream()
                .filter(f -> f.getFechaInicio() != null && !f.getFechaInicio().isBefore(hoy)) // incluye hoy
                .sorted(Comparator.comparing(Feria::getFechaInicio))
                .toList();

        model.addAttribute("ferias", feriasProximas);
        return "views/cliente/dashboard";
    }

    // Mis reservas
    @GetMapping("/reservas")
        public String verMisReservas(HttpSession session, Model model) {
            String clienteId = (String) session.getAttribute("clienteId");

            if (clienteId == null) {
                return "redirect:/cliente/login";
            }

            List<Reserva> reservas = reservaRepositorio.findByClienteId(clienteId);

            model.addAttribute("reservas", reservas);
            return "views/cliente/reservas/lista";
        }

    // Cancelar una reserva
    @GetMapping("/reservas/cancelar/{id}")
    public String cancelarReserva(@PathVariable String id, HttpSession session) {
        String clienteId = (String) session.getAttribute("clienteId");
        if (clienteId == null) return "redirect:/cliente/login";

        Reserva reserva = reservaRepositorio.findById(id).orElse(null);

        if (reserva != null && reserva.getClienteId().equals(clienteId)) {
            if ("pendiente".equals(reserva.getEstado())) {
                reserva.setEstado("cancelada");
                reservaRepositorio.save(reserva);

                Producto producto = productoRepositorio.findById(reserva.getProductoId()).orElse(null);
                if (producto != null) {
                    producto.setStock(producto.getStock() + reserva.getCantidad());
                    productoRepositorio.save(producto);
                }
            }
        }

        return "redirect:/cliente/reservas";
    }

    @GetMapping("/reservas/nueva/{id}")
    public String mostrarFormularioReserva(@PathVariable String id, HttpSession session, Model model) {
        String clienteId = (String) session.getAttribute("clienteId");
        if (clienteId == null) return "redirect:/cliente/login";

        Producto producto = productoRepositorio.findById(id).orElse(null);
        if (producto == null || producto.getStock() <= 0) {
            return "redirect:/cliente/productos?error";
        }

        model.addAttribute("producto", producto);
        return "views/cliente/reservas/formulario"; // Vista Thymeleaf
    }

    @PostMapping("/reservas/guardar")
        public String guardarReserva(
                @RequestParam String productoId,
                @RequestParam int cantidad,
                HttpSession session) {

            String clienteId = (String) session.getAttribute("clienteId");
            if (clienteId == null) return "redirect:/cliente/login";

            Producto producto = productoRepositorio.findById(productoId).orElse(null);
            if (producto == null || producto.getStock() < cantidad) {
                return "redirect:/cliente/productos?error";
            }

            // Crear la reserva
            Reserva reserva = new Reserva(clienteId, productoId, cantidad);
            reserva.setEstado("pendiente");
            reserva.setFechaReserva(LocalDate.now());
            reservaRepositorio.save(reserva);

            // Actualizar stock
            producto.setStock(producto.getStock() - cantidad);
            productoRepositorio.save(producto);

            return "redirect:/cliente/reservas?exito";
        }
}
