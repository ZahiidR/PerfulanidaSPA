package com.fullstack.perfulandiaSPA.Repository;

import com.fullstack.perfulandiaSPA.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// repositorio para la entidad modelo usuario (QUE REALMENTE ES UNA TABLA) y se extiende con JpaRepository
public interface usuarioRepository extends JpaRepository<Usuario, Long>{
    // metodo para buscar un usuario por EMAIl 
    Optional<Usuario> findByEmail(String email);
}