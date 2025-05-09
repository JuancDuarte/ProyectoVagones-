package com.edu.uptc.AplicacionVagones.Service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.uptc.AplicacionVagones.Entities.Material;
import com.edu.uptc.AplicacionVagones.Entities.Vagoneta;
import com.edu.uptc.AplicacionVagones.Managment.MaterialManagment;
import com.edu.uptc.AplicacionVagones.Managment.VagonManagment;
import com.edu.uptc.AplicacionVagones.Service.vagonetaService;

@Service("vagonService")
@Transactional
public class VagonetaServiceImplementation implements vagonetaService{
    @Autowired
    @Qualifier("CrudVagoneta")
    private VagonManagment va;
    
    @Autowired
    @Qualifier("CrudMaterial")
    private MaterialManagment ma; 

    List<Vagoneta> vagonetaLista = new ArrayList<>();
    List<Material> materiaLista = new ArrayList<>();

    @Override
    public List<Vagoneta> getListVagon(){
        return(List<Vagoneta>)va.findAll();
    }
    @Override
    public Vagoneta saveVagoneta(Vagoneta vagon) {
        return va.save(vagon);
    }

    @Override
    public Material saveMaterial(Material material) {
        return ma.save(material);
    }
    @Override
    public Vagoneta addMaterial(Long id, Material material) {
        Optional<Vagoneta> optionalVagoneta = va.findById(id);
        if(optionalVagoneta.isPresent()){
            Vagoneta vagonet = optionalVagoneta.get();
            material.setVagoneta(vagonet); 
            ma.save(material);
            vagonet.getMaterial().add(material);
            return va.save(vagonet);
        }
        return null;
    }
    @Override
    public Vagoneta getVagonetaById(Long id) {
        Optional<Vagoneta> optionalVagoneta = va.findById(id);
        return optionalVagoneta.orElse(null);
    }
    


}
