package com.feriaApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mensajes_contacto")
public class MensajeContacto {

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String mensaje;
    private boolean respondido = false;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public boolean isRespondido() {
        return respondido;
    }
    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }
}
