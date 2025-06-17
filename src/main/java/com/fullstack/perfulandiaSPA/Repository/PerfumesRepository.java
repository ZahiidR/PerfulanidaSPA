package com.fullstack.perfulandiaSPA.Repository;

import com.fullstack.perfulandiaSPA.Model.Perfumes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PerfumesRepository {
    // Arreglo que guardara todos los perfumes
    private List<Perfumes> listaPerfumes = new ArrayList<>();
    public PerfumesRepository() {
        //Agregar perfumes por defecto
        listaPerfumes.add(new Perfumes(1, "Y", "YSL", 119990, 100,"Aroma fresco",30));
    }
    //Metodo que retorna todos los perfumes
    public List<Perfumes> obtenerPefume(){
        return listaPerfumes;
    }

    //buscar un perfume por su id 
    public Perfumes buscarPorId(int id){
        for (Perfumes perfume :listaPerfumes) {
            if (perfume.getId() == id){
                return perfume;
            }
        }
        return null;
    }
    // Buscar un perfume por su nombre 
    public Perfumes buscarPorNombre(String nombre) {
        for (Perfumes perfume : listaPerfumes) {
            if (perfume.getNombrePerfume().equals(nombre)) {
                return perfume;
            }
        }
        return null;
    }

    public Perfumes guardar(Perfumes per) {
        // Generar nuevo ID secuencial
        long nuevoId = 1;
        for (Perfumes l : listaPerfumes) {
            if (l.getId() >= nuevoId) {
                nuevoId = l.getId() + 1;
            }
        }

        // Crear una nueva instancia con los datos del perfume recibido
        Perfumes perfume = new Perfumes();
        perfume.setId((int) nuevoId); // ID generado automáticamente
        perfume.setNombrePerfume(per.getNombrePerfume());
        perfume.setMarca(per.getMarca());
        perfume.setPrecio(per.getPrecio());
        perfume.setCantidadMl(per.getCantidadMl());
        perfume.setDescripcion(per.getDescripcion());
        perfume.setStock(per.getStock());
        
        listaPerfumes.add(perfume);

        return perfume;
    }

    public Perfumes actualizar(Perfumes per) {
       int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaPerfumes.size(); i++) {
            if (listaPerfumes.get(i).getId() == per.getId()) {
                id = per.getId();
                idPosicion = i;
            }
        }
        Perfumes perfumes1 = new Perfumes();
         perfumes1.setId(id);
         perfumes1.setNombrePerfume(per.getNombrePerfume());
         perfumes1.setMarca(per.getMarca());
         perfumes1.setPrecio(per.getPrecio());
         perfumes1.setCantidadMl(per.getCantidadMl());
 
         listaPerfumes.set(idPosicion, perfumes1);
         return perfumes1; 
     }


     public void eliminar(int id){
        listaPerfumes.removeIf(x -> x.getId() == id);
     }

     public int totalPerfumes(){
        return listaPerfumes.size();
     }
}



