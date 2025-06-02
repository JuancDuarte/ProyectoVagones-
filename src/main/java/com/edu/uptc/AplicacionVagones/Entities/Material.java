package com.edu.uptc.AplicacionVagones.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre_material;
    private double peso;
    @ManyToOne(fetch = FetchType.LAZY)
@   JoinColumn(name = "vagoneta_id")
@   JsonIgnore
    private Vagoneta vagoneta;
    private String tipo;

    public Material() {
    }
    public Material(Long id, String nombre_material, double peso, String tipo) {
        this.id = id;
        this.nombre_material = nombre_material;
        this.peso = peso;
        this.tipo = tipo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getnombre_material() {
        return nombre_material;
    }
    public void setnombre_material(String nombre_material) {
        this.nombre_material = nombre_material;
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
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
