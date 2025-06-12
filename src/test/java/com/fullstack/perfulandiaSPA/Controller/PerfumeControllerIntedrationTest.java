package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(perfumeController.class)

public class PerfumeControllerIntedrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private perfumeService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarPerfumes_debeRetornarListaJson() throws Exception {
        List<Perfumes> perfumes = List.of(
                new Perfumes(1,"Perfume1","Marca1", 37000,100),
                new Perfumes(2, "Perfume2", "Marca2", 45000, 100)
        );

        when(libroService.getPerfumes()).thenReturn(perfumes);

        mockMvc.perform(get("/api/v1/perfumes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Perfume1"));
    }

    @Test
    void agregarPerfume_debeGuardarYRetornarPerfume() throws Exception {
        Perfumes perfumes = new Perfumes(0, "Perfume1", "Marca1", 150000, 100);

        when(perfumeService.savePerfume(any(Perfumes.class))).thenReturn(perfumes);

        mockMvc.perform(post("/api/v1/perfumes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(perfumes)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo Perfume"));
    }

    @Test
    void buscarPerfume_porId_existente() throws Exception {
        Perfumes perfumes = new Perfumes(5, "LightAction", "Ed", 86990, 100);

        when(perfumeService.getPerfumeId(5)).thenReturn(perfumes);

        mockMvc.perform(get("/api/v1/perfumes/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("LightAction"));
    }

    @Test
    void eliminarPerfume_existente() throws Exception {
        when(perfumeService.deletePerfume(3)).thenReturn("perfume eliminado");

        mockMvc.perform(delete("/api/v1/perfumes/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("perfume eliminado"));
    }

    @Test
    void totalPerfumesV2_debeRetornarCantidad() throws Exception {
        when(perfumeService.totalPerfumesv2()).thenReturn(10);

        mockMvc.perform(get("/api/v1/perfumes/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}

