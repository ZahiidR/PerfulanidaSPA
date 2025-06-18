package com.fullstack.perfulandiaSPA.Repository;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Model.Perfumes;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class controlstockRepository {
     // Arreglo que guardara el stock
    private List<ControlStock> listaStocks = new ArrayList<>();
    public controlstockRepository() {
        //Agregar stock por defecto
        Perfumes perfumes = new Perfumes(1,"Y", "YSL", 11990,100,"AROMA FRESCO",30);
        listaStocks.add(new ControlStock(1,perfumes,30,"18-06-2025")); //AQUI SE LE PONE ALGO EN LOS PARAMETROS??
    }
    //Metodo que retorna todos los stock
    public List<ControlStock> obtenerStocks(){
        return listaStocks;
    }

    //buscar un control de stock por su id 
    public ControlStock buscarPorId(int id){
        for (ControlStock control :listaStocks) {
            if (control.getId() == id){
                return control;
            }
        }
        return null;
    }
  
    //guardar un nuevo control de stock
    public ControlStock guardar(ControlStock control) {
        // Generar nuevo ID secuencial
        long nuevoId = 1;
        for (ControlStock l : listaStocks) {
            if (l.getId() >= nuevoId) {
                nuevoId = l.getId() + 1;
            }
        }

        // Crear una nueva instancia con los datos del control stock recibido
        ControlStock nuevoControl = new ControlStock();
        nuevoControl.setId((int) nuevoId); // ID generado automáticamente
        nuevoControl.setPerfume(control.getPerfume());
        nuevoControl.setStockActual(control.getStockActual());
        nuevoControl.setFechaActualizacion(control.getFechaActualizacion());
        
        listaStocks.add(nuevoControl);

        return nuevoControl;
    }

    public ControlStock actualizar(ControlStock control) {
       int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaStocks.size(); i++) {
            if (listaStocks.get(i).getId() == control.getId()) {
                id = control.getId();
                idPosicion = i;
            }
        }
        ControlStock controlStock1 = new ControlStock();
         controlStock1.setId(id);
         controlStock1.setPerfume(control.getPerfume());
         controlStock1.setStockActual(control.getStockActual());
         controlStock1.setFechaActualizacion(control.getFechaActualizacion());
         
        listaStocks.set(idPosicion, controlStock1);
         return controlStock1; 
     }


     public void eliminar(int id){
        listaStocks.removeIf(x -> x.getId() == id);
     }

     public int totalControles(){
        return listaStocks.size();
     }

}
