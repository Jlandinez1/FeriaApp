package com.feriaApp.controllers;

import com.feriaApp.models.Producto;
import com.feriaApp.repository.ProductoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;



import java.util.List;

@Controller
@RequestMapping("/vendedor/productos")
public class ProductoController {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    
    @GetMapping
public String listarProductos(HttpSession session, Model model) {
    String vendedorId = (String) session.getAttribute("vendedorId");
    if (vendedorId == null) {
        return "redirect:/vendedor/login";
    }
    List<Producto> productos = productoRepositorio.findByVendedorId(vendedorId);
    model.addAttribute("productos", productos);
    System.out.println("Vendedor ID en sesión: " + vendedorId);  // Depuración
    return "views/vendedor/productos/lista";
}

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(HttpSession session, Model model) {
        String vendedorId = (String) session.getAttribute("vendedorId");
        model.addAttribute("producto", new Producto());
        System.out.println("Vendedor ID en sesión: " + vendedorId);  // Depuración
        return "views/vendedor/productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("stock") int stock,
            @RequestParam("categoria") String categoria,
            @RequestParam("imagen") MultipartFile imagen,
            HttpSession session) {

        String vendedorId = (String) session.getAttribute("vendedorId");
        if (vendedorId == null) return "redirect:/vendedor/login";

        Producto producto;
        if (id != null && !id.isEmpty()) {
            producto = productoRepositorio.findById(id).orElse(new Producto());
        } else {
            producto = new Producto();
            producto.setVendedorId(vendedorId);
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        // Ruta donde se guardará la imagen
        String uploadDir = "src/main/resources/static/img/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (!imagen.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                File fileToSave = new File(dir, fileName);
                imagen.transferTo(fileToSave);
                producto.setImagenUrl("/img/" + fileName); // Guardamos solo la ruta relativa

                System.out.println("🖼️ Imagen guardada en: " + fileToSave.getAbsolutePath());
                System.out.println("🌐 URL pública: /img/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        producto.setVendedorId(vendedorId);
        productoRepositorio.save(producto);

        return "redirect:/vendedor/productos";
    }


    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable String id, Model model) {
        Producto producto = productoRepositorio.findById(id).orElse(null);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "views/vendedor/productos/formulario";
        }
        return "redirect:/vendedor/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id) {
        productoRepositorio.deleteById(id);
        return "redirect:/vendedor/productos";
    }
}
