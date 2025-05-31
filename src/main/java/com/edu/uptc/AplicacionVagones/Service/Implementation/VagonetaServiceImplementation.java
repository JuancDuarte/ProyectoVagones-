package com.edu.uptc.AplicacionVagones.Service.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    @Override
    public List<Material> getListMaterials() {
        return(List<Material>)ma.findAll();
    }
    @Override
    public void entradaVagon(Long id, List<Material> materials) {
        Optional<Vagoneta> optionalVagoneta = va.findById(id);
        Runnable entradaMina = () -> {
            double cargaMaxima = optionalVagoneta.get().getCargaMaxima();
            double cargaActual = materials.stream().mapToDouble(Material::getPeso).sum();
            Random random = new Random();
            if (!va.existsById(id)) {
                System.out.println("El vagón con el ID: " + id + " no existe.");
                return;
            }   
            if(materials == null || materials.isEmpty()){
                System.out.println("El vagon esta vacio agrega materiales para minar");
                return;
            }
            if(optionalVagoneta.isPresent()){
                Vagoneta vagonet = optionalVagoneta.get();
                System.out.println("Vagoneta " + id + " ingresando a la mina con carga inicial de herramientas: " + cargaActual + " kg");
                while (cargaActual< cargaMaxima) {
                    long randomMaterialId = 4 + random.nextInt(8); // IDs entre 4 y 11
                    Optional<Material> optionalMaterial = ma.findById(randomMaterialId);
                     if (optionalMaterial.isPresent()) {
                            double peso = optionalMaterial.get().getPeso();

                        if (cargaActual + peso > cargaMaxima) {
                        System.out.println("No se puede agregar material (ID: " + randomMaterialId + ") - Exceso de carga.");
                        break;
                        }
                        Material nuevo = new Material();
                      //  nuevo.setId(optionalMaterial.get().getId());
                        nuevo.setNombreMaterial(optionalMaterial.get().getNombreMaterial());
                        nuevo.setPeso(peso);
                        nuevo.setVagoneta(optionalVagoneta.get());
                        ma.save(nuevo);
                        vagonet.setHoraEntrada(new Date());
                        vagonet.setEstado("Ocupado");
                        vagonet.getMaterial().add(nuevo);
                        cargaActual += peso;
                        System.out.println("Material añadido: " + nuevo.getNombreMaterial() + " - Peso: " + peso + "kg - Total: " + cargaActual + "kg");
                     }else {
                    System.out.println("Material con ID " + randomMaterialId + " no encontrado en base de datos.");
                 }
                }
            va.save(vagonet);
            System.out.println("Vagoneta " + id + " completó recolección. Carga total: " + cargaActual + "kg.");
        }
        };
        new Thread(entradaMina).start();
    }
    @Override
    public void salidaVagoneta(Long id) {
        Runnable salidaTask = () -> {
            Optional<Vagoneta> vagonOptional = va.findById(id);
            if (vagonOptional.isPresent()) {
                Vagoneta vagon = vagonOptional.get();
                vagon.setEstado("Disponible");
                vagon.setHoraSalida(new Date());
                
                System.out.println("Vagon " + id + " salió de la mina a las: "+ vagon.getHoraSalida()   );
                va.save(vagon);

            } else {
                System.out.println("Vagon con id : " + id + " no se encontró ");
            }
        };
        new Thread(salidaTask).start();
    }
    


}
