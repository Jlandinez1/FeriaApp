package com.feriaApp.models;
import java.time.LocalDate;

public class ReservaReporteDTO {
    private String productoNombre;
    private String clienteNombre;
    private int cantidadTotal;
    private LocalDate ultimaReserva;
    private long totalReservas;

    public ReservaReporteDTO(String productoNombre, String clienteNombre,
                            int cantidadTotal, LocalDate ultimaReserva, long totalReservas) {
        this.productoNombre = productoNombre;
        this.clienteNombre = clienteNombre;
        this.cantidadTotal = cantidadTotal;
        this.ultimaReserva = ultimaReserva;
        this.totalReservas = totalReservas;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public LocalDate getUltimaReserva() {
        return ultimaReserva;
    }

    public void setUltimaReserva(LocalDate ultimaReserva) {
        this.ultimaReserva = ultimaReserva;
    }

    public long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(long totalReservas) {
        this.totalReservas = totalReservas;
    }

    // Getters
    
}