package com.fullstack.perfulandiaSPA.Assemblers;

//Importar las clases necesarias para el modelo y controlador
import com.fullstack.perfulandiaSPA.Model.Carrito;
import com.fullstack.perfulandiaSPA.Controller.CarritoControllerv2;

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
public class carritoModelAssembler implements RepresentationModelAssembler<perfume, EntituModel<Perfume>> {
    @Override
    public @NonNull EntityModel<Perfume> toModel(Perfume p){
        return EntityModel.of(p,
        linkTo(methodOn(carritoControlerV2.class).verCarrito()).withRel("carrito"),
        linkTo(methodOn(carritoControlerV2.class).eliminarPerfume(p.getId())).withRel("eliminar"));  
    }
    
}
