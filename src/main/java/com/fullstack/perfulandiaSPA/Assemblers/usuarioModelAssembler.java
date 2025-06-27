package com.fullstack.perfulandiaSPA.Assemblers;

//Importar las clases necesarias para el modelo y controlador
import com.fullstack.perfulandiaSPA.Model.Usuario;
import com.fullstack.perfulandiaSPA.Controller.UsuarioControllerV2;

//Importar la clase static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importar la clase entityModel para usar los HATEOAS
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//Importar la anotacion NonNull para indicar que el metodo no acepta valores nulos
import org.springframework.lang.NonNull;

//Agregar la anotacion @Component para indicar que la clase usuarioModelAssembler es un componente spring y puede ser inyectada en otros componentes o coontroladores
@Component
public class usuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{ //Es una clase que implementa RepresentationModelAssembler para convertir un objeto usuario en un EntityModel
    //Usar la anotacion @Override para indicar que el siguiente metodo implementa un metodo de interfaz RepresentationModelAssembler
    @Override
    //Metodo EntityModel<usuario> para convertir un objeto de usuario y añadir los enlaces HATEOAS
    public @NonNull EntityModel<Usuario> toModel(Usuario u) {
        return EntityModel.of(u, 
        linkTo(methodOn(UsuarioControllerV2.class).registrar
        (u=null)).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).login
        (u)).withRel("login"));
    }    
}