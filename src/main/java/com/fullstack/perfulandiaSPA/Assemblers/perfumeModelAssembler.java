package com.fullstack.perfulandiaSPA.Assemblers;


//Importar las clases necesarias para el modelo y controlador
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Controller.PerfumeControllerv2;

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
public class perfumeModelAssembler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>> {
    @Override
    public @NonNull EntityModel<Perfume> toModel(Perfume p) {
        //El metodo LinkTo lo usamos para crear los enlaces HATEOAS para cada API y el methodOn reconoce el metodo REST del controller
        return EntityModel.of(p, 
        linkTo(methodOn(PerfumeControllerV2.class).buscarPerfumes(p.getId())).withSelfRel(),
        linkTo(methodOn(PerfumeControllerV2.class).listarPerfumes()).withRel("perfumes"),
        linkTo(methodOn(PerfumeControllerV2.class).eliminarPerfume(p.getId())).withRel("eliminar"),
        linkTo(methodOn(PerfumeControllerV2.class).actualizaPerfumes(p.getId(), p)).withRel("actualizar"));
    }
    
}
