package com.fullstack.perfulandiaSPA.Repository;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReporteRepository {
    private List<Reporte> listaReportes = new ArrayList<>();
    public ReporteRepository(){
        // Agregar reportes por defecto
        listaReportes.add(new Reporte(1,"Producto comprado con éxito","usuario1@gmaill.com","RECIBIDO"));
        listaReportes.add(new Reporte(2, "Producto enviado con exito", "usuario2@gmail.com", "EN PROCESO"));
        listaReportes.add(new Reporte(3, "Producto sin tock", "usuario3@gmaiñl.com","EN PROCESO" ));

    }  
    // Metodo que retorna todos los reportes
    public List<Reporte> obtenerReportes() {
        return listaReportes;
    }

    // Buscar un Reporte por su id
    public Reporte buscarPorId(int id) {
        for (Reporte reporte : listaReportes) {
            if (reporte.getId() == id) {
                return reporte;
            }
        }
        return null;
    }

    public Reporte guardar(Reporte rep) {
        // Generar nuevo ID secuencial
        long nuevoId = 1;
        for (Reporte l : listaReportes) {
            if (l.getId() >= nuevoId) {
                nuevoId = l.getId() + 1;
            }
}
     // Crear una nueva instancia con los datos del reporte recibido
        Reporte reporte = new Reporte();
        reporte.setId((int) nuevoId); // ID generado automáticamente
        reporte.setDescripcion(rep.getDescripcion());
        reporte.setUsuario(rep.getUsuario());
        reporte.setEstado(rep.getEstado());

         // Agregar el nuevo reporte a la lista
        listaReportes.add(reporte);

        return reporte;
}

     public Reporte actualizar(Reporte rep) {
        int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaReportes.size(); i++) {
            if (listaReportes.get(i).getId() == rep.getId()) {
                id = rep.getId();
                idPosicion = i;
            }
}

        Reporte reporte1 = new Reporte();
        reporte1.setId(id);
        reporte1.setDescripcion(rep.getDescripcion());
        reporte1.setUsuario(rep.getUsuario());
        reporte1.setEstado(rep.getEstado());
        

        listaReportes.set(idPosicion, reporte1);
        return reporte1;
    }
     public void eliminar(int id) {
     Reporte reporte = buscarPorId(id);
         if (reporte != null) {
         listaReportes.remove(reporte);
         }
        }
    public int totalReportes(){
        return listaReportes.size();
    }
}
