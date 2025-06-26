package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import com.fullstack.perfulandiaSPA.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Importar las librerias de swagger para la documentacion de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("http://10.155.64.162:8080/api/v1/reporte")
@Tag(name = "Reporte", description = "Operaciones sobre el reporte de Perfumes")
public class ReporteController {
     @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Desplegar la lista de los reportes", description = "Despliega una lista de todos los reportes disponibles")
    @GetMapping
    public List<Reporte> listarReportes() {
        return reporteService.getReportes();
    }

    @Operation(summary = "Agregar nuevo reporte", description = "Agrega nuevo reporte de perfumes")
    @PostMapping
    public Reporte agregarReporte(@RequestBody Reporte reporte) {
        return reporteService.saveReporte(reporte);
    }

    @Operation(summary = "Buscar reportes", description = "Busca reportes especificos por su ID")
    @GetMapping("{id}")
    public Reporte buscarReporte(@PathVariable int id){
        return reporteService.getReporte(id);
    }

    @Operation(summary = "Actualizar informacion del reporte", description = "Actualiza los detalles del reporte")
    @PutMapping("{id}")
    public Reporte actualizarReporte(@PathVariable int id, @RequestBody Reporte reporte){
        // el id lo usaremos mas adelante
        return reporteService.updateReporte(reporte);
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina reporte especifico por su ID")
    @DeleteMapping("{id}")
    public String eliminarReporte(@PathVariable int id) {
        return reporteService.deleteReporte(id);
    }

    @Operation(summary = "Contar reportes generados", description = "Devuelve la cantidad de reportes")
    @GetMapping("/total")
    public int totalReporteV2() {
        return reporteService.totalReporteV2();
    }
}
