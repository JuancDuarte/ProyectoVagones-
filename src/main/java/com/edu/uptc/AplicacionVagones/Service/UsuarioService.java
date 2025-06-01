package com.edu.uptc.AplicacionVagones.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.uptc.AplicacionVagones.Entities.UsuarioEntity;
import com.edu.uptc.AplicacionVagones.Managment.UsuarioManagment;

public class UsuarioService {
    @Autowired
    private UsuarioManagment um;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioEntity usuario = um.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    System.out.println("Cargando usario de DB: "+ username);
    return User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .authorities(usuario.getTipoUsuario().name())
        .build();
    }
}
