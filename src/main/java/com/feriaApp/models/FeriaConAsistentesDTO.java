package com.feriaApp.models;

import java.time.LocalDate;

public class FeriaConAsistentesDTO {
    private String id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String ubicacion;
    private long totalAsistentes;

    public FeriaConAsistentesDTO(String id, String nombre, LocalDate fechaInicio,
                                 LocalDate fechaFin, String ubicacion, long totalAsistentes) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ubicacion = ubicacion;
        this.totalAsistentes = totalAsistentes;
    }

    // Getters

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public String getUbicacion() { return ubicacion; }
    public long getTotalAsistentes() { return totalAsistentes; }
}