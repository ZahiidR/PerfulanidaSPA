package com.fullstack.perfulandiaSPA.Assemblers.Controller;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fullstack.perfulandiaSPA.Assemblers.usuarioModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("http://192.168.119.154:8080/api/v2/usuarios") //url base para peticiones
@CrossOrigin
@Tag(name = "Usuario", description = "Operaciones sobre el usuario")
public class UsuarioControllerV2 {
    @Autowired
    private usuarioService serv;

    @Autowired
    private usuarioModelAssembler assembler;

    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @PostMapping(value = "/registrar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> registrar(@RequestBody Usuario u) {
        Usuario creado = serv.registrar(u);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).registrar(creado)).toUri())
                .body(assembler.toModel(creado));
    }

    @Operation(summary = "Inicia sesion", description = "Autentica a un usuario con el email y su contraseña")
    @PostMapping(value = "/login", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Map<String, String>>> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String, String> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("result", "OK");
            response.put("nombre", user.get().getNombre());
            response.put("email", user.get().getEmail());
            response.put("password", user.get().getPassword());
        } else {
            response.put("result", "Error");
        }
        
        EntityModel<Map<String, String>> model = EntityModel.of(response);
        model.add(linkTo(methodOn(UsuarioControllerV2.class).login(u)).withSelfRel());
        model.add(linkTo(methodOn(UsuarioControllerV2.class).registrar(new Usuario())).withRel("registrar"));

        return ResponseEntity.ok(model);
    }
        
}
