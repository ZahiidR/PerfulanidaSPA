package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@WebMvcTest(perfumeController.class)

public class perfumeControllerIntegrationTest {
     @Autowired
    private MockMvc mockMvc;

    @MockBean
    private perfumeService perfumeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarPerfumes_debeRetornarListaJson() throws Exception {
        List<Perfumes> perfumes = List.of(
                new Perfumes(1, "vip 212", "Carolina Herrera", 89990, 20,"Fragancia fresca",20),
                new Perfumes(2, "Perfume 2", "desconocido", 50000, 20, "neuva fragancia", 10)
        );

        when(perfumeService.getPerfumes()).thenReturn(perfumes);

        mockMvc.perform(get("/api/v1/perfumes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombrePerfume").value("vip 212"));
    }

    @Test
    void agregarPerfumes_debeGuardarYRetornarPerfumes() throws Exception {
        Perfumes perfumes = new Perfumes(1, "perfume1", "carolina herrera", 59990, 100, "Nueva fragancia", 12);

        when(perfumeService.savePerfume(any(Perfumes.class))).thenReturn(perfumes);

        mockMvc.perform(post("/api/v1/perfumes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(perfumes)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombrePerfume").value("perfume1"));
    }

    @Test
    void buscarPerfume_porId_existente() throws Exception {
        Perfumes perfumes = new Perfumes(1, "vip 212", "carolina herrera", 89990, 200, "fragancia fresca", 20);

        when(perfumeService.getPerfumeId(5)).thenReturn(perfumes);

        mockMvc.perform(get("/api/v1/perfumes/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombrePerfume").value("vip 212"));
    }

    @Test
    void eliminarPerfumes_existente() throws Exception {
        when(perfumeService.deletePerfume(3)).thenReturn("producto eliminado");

        mockMvc.perform(delete("/api/v1/perfumes/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("producto eliminado"));
    }

    @Test
    void totalPerfumesv2_debeRetornarCantidad() throws Exception {
        when(perfumeService.totalPerfumesv2()).thenReturn(10);

        mockMvc.perform(get("/api/v1/perfumes/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

}
