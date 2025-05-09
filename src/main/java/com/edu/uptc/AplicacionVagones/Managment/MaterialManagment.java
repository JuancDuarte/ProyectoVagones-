package com.edu.uptc.AplicacionVagones.Managment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.uptc.AplicacionVagones.Entities.Material;

@Repository("CrudMaterial")
public interface MaterialManagment extends CrudRepository<Material, Long> {

}
