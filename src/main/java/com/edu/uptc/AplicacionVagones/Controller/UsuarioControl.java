package com.edu.uptc.AplicacionVagones.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.uptc.AplicacionVagones.Entities.UsuarioEntity;
import com.edu.uptc.AplicacionVagones.Managment.UsuarioManagment;

@RestController
@RequestMapping("/usuarios/")
@CrossOrigin(origins="*")
public class UsuarioControl {
     @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioManagment usuarioMg;

    @PostMapping(path="/crear")
    public UsuarioEntity crearUsuario(@RequestBody UsuarioEntity usuario) {
        usuario.setPassword(usuario.getPassword());
        return usuarioMg.save(usuario);
    }
    
}
