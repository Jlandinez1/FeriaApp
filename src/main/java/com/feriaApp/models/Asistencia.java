package com.feriaApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "asistencias")
public class Asistencia {
    @Id
    private String id;
    private String clienteId;
    private String feriaId;
    private boolean confirmada;

    public Asistencia() {}

    public Asistencia(String clienteId, String feriaId, boolean confirmada) {
        this.clienteId = clienteId;
        this.feriaId = feriaId;
        this.confirmada = confirmada;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getFeriaId() { return feriaId; }
    public void setFeriaId(String feriaId) { this.feriaId = feriaId; }

    public boolean isConfirmada() { return confirmada; }
    public void setConfirmada(boolean confirmada) { this.confirmada = confirmada; }
}