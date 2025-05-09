package com.edu.uptc.AplicacionVagones.Managment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.uptc.AplicacionVagones.Entities.Vagoneta;

@Repository("CrudVagoneta")
public interface VagonManagment extends CrudRepository<Vagoneta, Long>{

}