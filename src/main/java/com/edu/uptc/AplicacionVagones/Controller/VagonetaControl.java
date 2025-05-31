package com.edu.uptc.AplicacionVagones.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.uptc.AplicacionVagones.Entities.Material;
import com.edu.uptc.AplicacionVagones.Entities.PesoDTO;
import com.edu.uptc.AplicacionVagones.Entities.Vagoneta;
import com.edu.uptc.AplicacionVagones.Service.Implementation.VagonetaServiceImplementation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("vagoneta")
@CrossOrigin(origins="*")
public class VagonetaControl {
@Autowired
@Qualifier("vagonService")
private VagonetaServiceImplementation vsi;

@PutMapping(path = "/añadirMaterial/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vagoneta> putMaterial(@RequestBody Material material, @PathVariable Long id) {
        Vagoneta updatedVagoneta = vsi.addMaterial(id, material);
            if (updatedVagoneta != null) {
                return ResponseEntity.ok(updatedVagoneta);
            } else {
                return ResponseEntity.notFound().build();
            }
}

@GetMapping(path = "/Calcularpeso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PesoDTO> obtenerCargaVagon(@PathVariable long id) {
        Vagoneta vagoneta = vsi.getVagonetaById(id);

        if (vagoneta != null) {
            double pesoTotal = 0.0;
    
            if (vagoneta.getMaterial() != null) {
                for (Material material : vagoneta.getMaterial()) {
                    pesoTotal += material.getPeso();
                }
            }
    
            String mensaje = "La carga total de la vagoneta con ID " + id + " es de " + pesoTotal + " kg.";
            PesoDTO dto = new PesoDTO(mensaje, pesoTotal, vagoneta);
            return ResponseEntity.ok(dto);
        } else {
            PesoDTO dto = new PesoDTO("Vagoneta con ID " + id + " no encontrada.", 0.0, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }
@GetMapping(path="/listarVagonetas",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Vagoneta> listarVagonetas() {
        return vsi.getListVagon();
    }
@GetMapping(path="/listarMateriales",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Material> listarMateriales() {
        return vsi.getListMaterials();
    }
@PutMapping(path="/EntradaMina", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Vagoneta> entradaMina( @PathVariable Long id) {
        Vagoneta updatedVagoneta = vsi.getVagonetaById(id);
            if (updatedVagoneta != null) {
                vsi.entradaVagon(id, updatedVagoneta.getMaterial());
                return ResponseEntity.ok(updatedVagoneta);
            } else {
                return ResponseEntity.notFound().build();
            }
}
@PutMapping(path="/salidaMina", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String salidaVehiculo(@PathVariable Long id) {
        vsi.salidaVagoneta(id);
        return "Vehículo está saliendo. Revisa la consola para el proceso completo.";
}

}
