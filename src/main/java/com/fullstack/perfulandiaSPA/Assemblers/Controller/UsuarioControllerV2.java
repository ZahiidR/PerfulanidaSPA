package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Usuario;
import com.fullstack.perfulandiaSPA.Service.usuarioService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("http://192.168.119.154:8080/api/v2/usuarios") //url base para peticiones
@CrossOrigin
@Tag(name = "Usuario", description = "Operaciones sobre el usuario")
public class UsuarioControllerV2 {
    @Autowired
    private usuarioService serv;

    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario u) {
        return serv.registrar(u);
    }

    @Operation(summary = "Inicia sesion", description = "Autentica a un usuario con el email y su contraseña")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String , String> response = new HashMap<>();
        if(user.isPresent()){
            response.put("result", "OK");
            response.put("nombre", user.get().getNombre());
            response.put("email", user.get().getEmail());
        }else{
            response.put("result", "Error");
        }
        return response;
        
        
    }
    
    
}
