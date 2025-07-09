package com.fullstack.perfulandiaSPA.Assemblers;


//Importar las clases necesarias para el modelo y controlador
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Controller.ControlStockControllerV2;

//Importar la clase static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importar la clase entityModel para usar los HATEOAS
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//Importar la anotacion NonNull para indicar que el metodo no acepta valores nulos
import org.springframework.lang.NonNull;

//Agregar la anotacion @Component para indicar que la clase usuarioModelAssembler es un componente 
//spring y puede ser inyectada en otros componentes o coontroladores
@Component
public class controlStockModelAssembler implements RepresentationModelAssembler<ControlStock, EntityModel<ControlStock>> {
    @Override
    public @NonNull EntityModel<ControlStock> toModel(ControlStock controlStock) {
        //El metodo LinkTo lo usamos para crear los enlaces HATEOAS para cada API y el methodOn reconoce el 
        //metodo REST del controller
        return EntityModel.of(controlStock, 
        linkTo(methodOn(ControlStockControllerV2.class).buscarControlStock(controlStock.getId())).withSelfRel(),
        linkTo(methodOn(ControlStockControllerV2.class).listarControlStocks()).withRel("Stocks"),
        linkTo(methodOn(ControlStockControllerV2.class).eliminarControl(controlStock.getId())).withRel("eliminar"),
        linkTo(methodOn(ControlStockControllerV2.class).actualizaControlStock(controlStock.getId(), controlStock)).withRel("actualizar"));
    }
    
}
