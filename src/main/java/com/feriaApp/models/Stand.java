package com.feriaApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stands")
public class Stand {
    @Id private String id;
    private String numero;
    private boolean disponible = true;
    private String ubicacion; // puede ser A1, B2, etc.
    private int limiteProductos;
    private String idVendedor;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getLimiteProductos() {
		return limiteProductos;
	}
	public void setLimiteProductos(int limiteProductos) {
		this.limiteProductos = limiteProductos;
	}
	public String getIdVendedor() {
		return idVendedor;
	}
	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}


}
