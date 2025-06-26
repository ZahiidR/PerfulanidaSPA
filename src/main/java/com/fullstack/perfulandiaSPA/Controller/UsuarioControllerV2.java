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


@RestController
@RequestMapping("/api/v2/usuarios") //url base para peticiones
@CrossOrigin

public class UsuarioControllerV2 {
    @Autowired
    private usuarioService serv;

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario u) {
        return serv.registrar(u);
    }

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
