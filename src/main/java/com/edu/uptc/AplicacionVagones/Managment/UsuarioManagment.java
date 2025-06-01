package com.edu.uptc.AplicacionVagones.Managment;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.edu.uptc.AplicacionVagones.Entities.UsuarioEntity;

public interface UsuarioManagment extends CrudRepository<UsuarioEntity, Long>{
    Optional<UsuarioEntity> findByUsername(String username);
}
