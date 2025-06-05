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

    @Autowired
    @Qualifier("RegistroPdf")
    private RegistroMina registroMina;
    
    @Autowired
    @Qualifier("PagoMinerales")
    private PagoMinerales pagoMinerales;

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
        if ("mineral".equalsIgnoreCase(material.getTipo())) {
            System.out.println("No se pueden agregar nuevos materiales de tipo mineral.");
            } else if ("herramienta".equalsIgnoreCase(material.getTipo())) {
                ma.save(material);
            }
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

    if (materials == null || materials.isEmpty()) {
        System.out.println("La lista de materiales está vacía. Agrega materiales antes de entrar a la mina.");
        return;
    }

    if (!optionalVagoneta.isPresent()) {
        System.out.println("El vagón con el ID: " + id + " no existe.");
        return;
    }

    Vagoneta vagoneta = optionalVagoneta.get();

    if ("Ocupado".equalsIgnoreCase(vagoneta.getEstado())) {
        System.out.println("El vagón con el ID " + id + " está ocupado. Usa un vagón disponible.");
        return;
    }

    Runnable entradaMina = () -> {
        double cargaMaxima = vagoneta.getcarga_maxima();
        double cargaActual = materials.stream().mapToDouble(Material::getPeso).sum();

        if (cargaActual > cargaMaxima) {
            System.out.println("El vagón con el ID: " + id + " ya no tiene capacidad para almacenar más materiales.");
            return;
        }

        Random random = new Random();

        System.out.println("Vagoneta " + id + " ingresando a la mina con carga inicial: " + cargaActual + " kg.");

        while (cargaActual < cargaMaxima) {
            long randomMaterialId = 4 + random.nextInt(8); // IDs entre 4 y 11
            Optional<Material> optionalMaterial = ma.findById(randomMaterialId);

            if (optionalMaterial.isPresent()) {
                Material baseMaterial = optionalMaterial.get();
                double peso = baseMaterial.getPeso();

                if (cargaActual + peso > cargaMaxima) {
                    System.out.println("No se puede agregar material (ID: " + randomMaterialId + ") - Exceso de carga.");
                    break;
                }

                Material nuevo = new Material();
                nuevo.setnombre_material(baseMaterial.getnombre_material());
                nuevo.setPeso(peso);
                nuevo.setTipo(baseMaterial.getTipo());
                nuevo.setVagoneta(vagoneta);

                ma.save(nuevo);

                vagoneta.sethora_entrada(new Date());
                vagoneta.setEstado("Ocupado");
                vagoneta.getMaterial().add(nuevo);

                cargaActual += peso;

                System.out.println("Material añadido: " + nuevo.getnombre_material() +
                        " - Peso: " + peso + "kg - Total acumulado: " + cargaActual + "kg");
            } else {
                System.out.println("Material con ID " + randomMaterialId + " no encontrado.");
            }
        }

        va.save(vagoneta);
        System.out.println("Vagoneta " + id + " completó recolección. Carga total: " + cargaActual + "kg.");
    };

    new Thread(entradaMina).start();
}
    @Override
    public void salidaVagoneta(Long id) {
        Vagoneta vagon = va.findById(id).orElse(null);
        if (vagon != null) {
            vagon.getMaterial().size(); 
        }
        Runnable salidaTask = () -> {
            Optional<Vagoneta> vagonOptional = va.findById(id);
            double cargaActual = vagonOptional.get().getMaterial().stream().mapToDouble(Material::getPeso).sum();
            if (!va.existsById(id)) {
                System.out.println("El vagón con el ID: " + id + " no existe.");
                return;
            }   
            if(vagonOptional.get().getEstado() == "Disponible"){
                System.out.println("El vagon con el ID "+ id + "esta " + vagonOptional.get().getEstado()
                + "fuera de la mina");
                return;
            }
            if (vagonOptional.isPresent()) {
                vagon.setEstado("Disponible");
                vagon.sethora_salida(new Date());
                registroMina.generarRegistroPDF(id, cargaActual, vagon.gethora_entrada(), vagon.gethora_salida(),
                 vagon.getMaterial());
                
                System.out.println("Vagon " + id + " salió de la mina a las: "+ vagon.gethora_salida()   );
                va.save(vagon);

            } else {
                System.out.println("Vagon con id : " + id + " no se encontró ");
            }
        };
        new Thread(salidaTask).start();
    }
     @Transactional
    public double venderMateriales(Long idVagoneta) {
        Optional<Vagoneta> optionalVagoneta = va.findById(idVagoneta);
        if (optionalVagoneta.isEmpty()) {
            throw new RuntimeException("Vagoneta con id " + idVagoneta + " no encontrada");
        }

        Vagoneta vagoneta = optionalVagoneta.get();
        List<Material> materiales = new ArrayList<>(vagoneta.getMaterial());
        double totalVenta = 0;

        for (Material material : materiales) {
            if ("mineral".equalsIgnoreCase(material.getTipo())) {
                if (material.getId() >= 4 && material.getId() <= 11) {
                    material.setVagoneta(null); // desasociar
                    totalVenta += material.getPeso() * 1000;
                    ma.save(material);
                } else {
                    totalVenta += material.getPeso() * 1000;
                    ma.delete(material);
                }
            } else if ("herramienta".equalsIgnoreCase(material.getTipo())) {
                material.setVagoneta(null);
                ma.save(material);
            }
        }
        pagoMinerales.generarPagoPDF(idVagoneta, vagoneta.getcarga_maxima(), vagoneta.gethora_entrada(), vagoneta.gethora_salida(), materiales, totalVenta);
        vagoneta.getMaterial().clear();
        vagoneta.sethora_entrada(null);
        vagoneta.sethora_salida(null);
        va.save(vagoneta);

        return totalVenta;
    }
    
    


}
