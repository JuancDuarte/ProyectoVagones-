package com.edu.uptc.AplicacionVagones.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
    @Id
    private Long id;
    private String nombreMaterial;
    private double peso;
    @ManyToOne
    @JoinColumn(name = "vagoneta_id")
    private Vagoneta vagoneta;

    public Material() {
    }
    public Material(Long id, String nombreMaterial, double peso) {
        this.id = id;
        this.nombreMaterial = nombreMaterial;
        this.peso = peso;
        this.vagoneta = vagoneta;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreMaterial() {
        return nombreMaterial;
    }
    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public Vagoneta getVagoneta() {
        return vagoneta;
    }
    public void setVagoneta(Vagoneta vagoneta) {
        this.vagoneta = vagoneta;
    }

}
