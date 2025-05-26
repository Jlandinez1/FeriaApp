package com.feriaApp.models;

public class ProductoConVendedorDTO {

    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String categoria;
    private String imagenUrl;
    private String vendedorNombre;

    public ProductoConVendedorDTO(String id, String nombre, double precio, String descripcion,
                                  int stock, String categoria, String imagenUrl, String vendedorNombre) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.categoria = categoria;
        this.imagenUrl = imagenUrl;
        this.vendedorNombre = vendedorNombre;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public int getStock() { return stock; }
    public String getCategoria() { return categoria; }
    public String getImagenUrl() { return imagenUrl; }
    public String getVendedorNombre() { return vendedorNombre; }
}