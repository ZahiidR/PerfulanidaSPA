package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Model.Reporte;
import com.fullstack.perfulandiaSPA.Service.ReporteService;
import com.fullstack.perfulandiaSPA.Service.perfumeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReporteController.class)
public class reporteControllerIntegrationTest {
         @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarReportes_debeRetornarListaJson() throws Exception {
        List<Reporte> reportes = List.of(
                new Reporte(1, "Stock bajo", "admin1","Alerta"),
                new Reporte(2, "Revision completada", "usuario1","OK")
        );

        when(reporteService.getReportes()).thenReturn(reportes);

        mockMvc.perform(get("/api/v1/reporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].descripcion").value("Stock bajo"));
    }

    @Test
    void agregarReportes_debeGuardarYRetornarReportes() throws Exception {
        Reporte reporte = new Reporte(3, "Nuevo reporte generado", "interno","Pendiente");

        when(reporteService.saveReporte(any(Reporte.class))).thenReturn(reporte);

        mockMvc.perform(post("/api/v1/reporte")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("interno"));
    }

    @Test
    void buscarReporte_porId_existente() throws Exception {
        Reporte reporte = new Reporte(4, "El sistema esta verificando", "admin", "OK");

        when(reporteService.getReporte(4)).thenReturn(reporte);

        mockMvc.perform(get("/api/v1/reporte/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("OK"));
    }

    @Test
    void eliminarReporte_existente() throws Exception {
        when(reporteService.deleteReporte(5)).thenReturn("Reporte eliminado");

        mockMvc.perform(delete("/api/v1/reporte/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reporte eliminado"));
    }

    @Test
    void totalReportev2_debeRetornarCantidad() throws Exception {
        when(reporteService.totalReporteV2()).thenReturn(7);

        mockMvc.perform(get("/api/v1/reporte/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

}


