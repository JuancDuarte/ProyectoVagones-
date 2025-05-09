package com.edu.uptc.AplicacionVagones.Entities;

public class PesoDTO {
    private String mensaje;
    private Double carga;
    private Vagoneta vagoneta;
    public PesoDTO(String mensaje, Double carga, Vagoneta vagoneta) {
        this.mensaje = mensaje;
        this.carga = carga;
        this.vagoneta = vagoneta;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public Double getCarga() {
        return carga;
    }
    public void setCarga(Double carga) {
        this.carga = carga;
    }
    public Vagoneta getVagonet() {
        return vagoneta;
    }
    public void setVagonet(Vagoneta vagoneta) {
        this.vagoneta = vagoneta;
    }

}
