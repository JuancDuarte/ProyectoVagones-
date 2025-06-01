package com.edu.uptc.AplicacionVagones.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.edu.uptc.AplicacionVagones.Entities.TipoUsuario;
import com.edu.uptc.AplicacionVagones.Entities.UsuarioEntity;
import com.edu.uptc.AplicacionVagones.Managment.UsuarioManagment;

@Component
public class DefaultInitializer implements CommandLineRunner {
     @Autowired
    private UsuarioManagment usuarioRepository;


    @Override
    public void run(String... args) throws Exception {
    String username = "Juan";
    if (!usuarioRepository.findByUsername(username).isPresent()) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(username);
        usuario.setPassword("vagones15");
        usuario.setTipoUsuario(TipoUsuario.ROLE_TRABAJADOR);

        usuarioRepository.save(usuario);

        System.out.println("Usuario administrador "+usuario.getUsername() +"creado con éxito.");
    } else {
    System.out.println("Usuario ya existe.");
    }
    }

}
