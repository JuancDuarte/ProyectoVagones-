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
    private double cargaMaxima;
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaEntrada;
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSalida;
    @OneToMany(mappedBy = "vagoneta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Material> Material;
    public Vagoneta(long id, double cargaMaxima, String estado, Date horaEntrada, Date horaSalida,
            List<com.edu.uptc.AplicacionVagones.Entities.Material> material) {
        this.id = id;
        this.cargaMaxima = cargaMaxima;
        this.estado = estado;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        Material = material;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getCargaMaxima() {
        return cargaMaxima;
    }
    public void setCargaMaxima(double cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public Date getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }
    public List<Material> getMaterial() {
        return Material;
    }
    public void setMaterial(List<Material> material) {
        Material = material;
    }
}
