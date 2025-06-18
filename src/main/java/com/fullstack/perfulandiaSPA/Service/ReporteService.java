package com.fullstack.perfulandiaSPA.Service;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import com.fullstack.perfulandiaSPA.Repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReporteService {
     @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> getReportes(){
        return reporteRepository.obtenerReportes();
    }

    public Reporte saveReporte(Reporte reporte){
        return reporteRepository.guardar(reporte);
    }
    public Reporte getReporte(int id) {
        return reporteRepository.buscarPorId(id);
    }

    public Reporte updateReporte(Reporte reporte) {
        return reporteRepository.actualizar(reporte);
    }

    public String deleteReporte(int id) {
        reporteRepository.eliminar(id);
        return "Reporte eliminado";
    }

     public int totalReportes() {
        return reporteRepository.obtenerReportes().size();
    }

    
    public int totalReporteV2() {
        return reporteRepository.totalReportes();
    }
}
