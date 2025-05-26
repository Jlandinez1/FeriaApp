package com.feriaApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vendedores")
public class Vendedor {

    @Id
    private String id;

    private String nombre;
    private String apellidos;
    private String correo;
    private String contraseña;
    private String cedula;
    private String telefono;
    private String estado = "pendiente"; // por defecto. Puede ser "pendiente", "aprobado", "rechazado"


    private String runt;
    private String nombreEmprendimiento;
    private String categoriaEmprendimiento;
    private String infoEmprendimiento;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRunt() { return runt; }
    public void setRunt(String runt) { this.runt = runt; }

    public String getNombreEmprendimiento() { return nombreEmprendimiento; }
    public void setNombreEmprendimiento(String nombreEmprendimiento) { this.nombreEmprendimiento = nombreEmprendimiento; }

    public String getCategoriaEmprendimiento() { return categoriaEmprendimiento; }
    public void setCategoriaEmprendimiento(String categoriaEmprendimiento) { this.categoriaEmprendimiento = categoriaEmprendimiento; }

    public String getInfoEmprendimiento() { return infoEmprendimiento; }
    public void setInfoEmprendimiento(String infoEmprendimiento) { this.infoEmprendimiento = infoEmprendimiento; }
    
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

}