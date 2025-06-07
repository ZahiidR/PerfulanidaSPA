package com.fullstack.perfulandiaSPA.Service;

import com.fullstack.perfulandiaSPA.Repository.usuarioRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.perfulandiaSPA.Model.Usuario;

@Service
public class usuarioService {
     @Autowired // para sincronizar el repositorio de usuario
    private usuarioRepository repo;

    // metodo para registrar usuarios
    public Usuario registrar(Usuario u){
        return repo.save(u); // verificar si el usuario ya existe en la base de datos
    }

    //metodo que autentificar los usuarios
    public Optional<Usuario> autenticar(String email, String password){
        return repo.findByEmail(email).filter(u -> u.getPassword().equals(password));
    }
}
