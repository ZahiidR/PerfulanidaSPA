package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import com.fullstack.perfulandiaSPA.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte")

public class ReporteController {
     @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> listarReportes() {
        return reporteService.getReportes();
    }

    @PostMapping
    public Reporte agregarReporte(@RequestBody Reporte reporte) {
        return reporteService.saveReporte(reporte);
    }

    @GetMapping("{id}")
    public Reporte buscarReporte(@PathVariable int id){
        return reporteService.getReporte(id);
    }

    @PutMapping("{id}")
    public Reporte actualizarReporte(@PathVariable int id, @RequestBody Reporte reporte){
        // el id lo usaremos mas adelante
        return reporteService.updateReporte(reporte);
    }

    @DeleteMapping("{id}")
    public String eliminarReporte(@PathVariable int id) {
        return reporteService.deleteReporte(id);
    }


    @GetMapping("/total")
    public int totalReporteV2() {
        return reporteService.totalReporteV2();
    }
}
