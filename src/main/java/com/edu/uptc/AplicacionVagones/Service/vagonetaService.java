package com.edu.uptc.AplicacionVagones.Service;

import java.util.List;

import com.edu.uptc.AplicacionVagones.Entities.Material;
import com.edu.uptc.AplicacionVagones.Entities.Vagoneta;

public interface vagonetaService {
    public abstract List<Vagoneta> getListVagon();
    public abstract Vagoneta saveVagoneta(Vagoneta vagon);
    public abstract Material saveMaterial(Material material);
    public abstract Vagoneta addMaterial(Long id, Material material);
    public abstract Vagoneta getVagonetaById(Long id);
    public abstract List<Material> getListMaterials();
    public abstract void entradaVagon(Long id, List<Material> materials);
    public abstract void salidaVagoneta(Long id);
} 
