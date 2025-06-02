package com.edu.uptc.AplicacionVagones.Entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="vagoneta")
public class Vagoneta {

    @Id
    private long id;
    private double carga_maxima;
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora_entrada;
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora_salida;
    @OneToMany(mappedBy = "vagoneta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Material> Material;
    public Vagoneta() {
    }
    public Vagoneta(long id, double carga_maxima, String estado, Date hora_entrada, Date hora_salida,
            List<com.edu.uptc.AplicacionVagones.Entities.Material> material) {
        this.id = id;
        this.carga_maxima = carga_maxima;
        this.estado = estado;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        Material = material;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getcarga_maxima() {
        return carga_maxima;
    }
    public void setcarga_maxima(double carga_maxima) {
        this.carga_maxima = carga_maxima;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date gethora_entrada() {
        return hora_entrada;
    }
    public void sethora_entrada(Date hora_entrada) {
        this.hora_entrada = hora_entrada;
    }
    public Date gethora_salida() {
        return hora_salida;
    }
    public void sethora_salida(Date hora_salida) {
        this.hora_salida = hora_salida;
    }
    public List<Material> getMaterial() {
        return Material;
    }
    public void setMaterial(List<Material> material) {
        Material = material;
    }
}
