package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Assemblers.Controller.carritoController;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(carritoController.class)
public class carritoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private perfumeService perfumeService;

    private Perfumes perfumeEjemplo;

    @BeforeEach
    void setUp() {
        perfumeEjemplo = new Perfumes(1, "vip 212", "carolina herrera", 59990, 200, "nueva fragancia", 13);
    }

    @Test
    void agregarPerfume_alCarrito_debeResponderConfirmacion() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);

        mockMvc.perform(post("/api/v1/carrito/agregar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfume agregado al carrito: vip 212"));
    }

    @Test
    void verCarrito_debeMostrarPerfumesAgregados() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(get("/api/v1/carrito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePerfume").value("vip 212"));
    }

    @Test
    void eliminarPerfume_delCarrito_debeEliminarCorrectamente() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(delete("/api/v1/carrito/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfume eliminado del carrito"));
    }

    @Test
    void vaciarCarrito_debeResponderCorrectamente() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(delete("/api/v1/carrito/vaciar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Carrito vaciado"));
    }

    @Test
    void totalLibrosCarrito_debeRetornarCantidad() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(get("/api/v1/carrito/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

}
