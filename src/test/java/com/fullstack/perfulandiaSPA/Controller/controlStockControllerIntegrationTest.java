package com.fullstack.perfulandiaSPA.Controller;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Service.ControlStockService;
import com.fullstack.perfulandiaSPA.Service.perfumeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ControlStockController.class)
public class controlStockControllerIntegrationTest {
      @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControlStockService controlStockService;
       @MockBean
    private perfumeService perfumeService;

    private Perfumes perfumeEjemplo;

    @BeforeEach
    void setUp() {
        perfumeEjemplo = new Perfumes(1, "vip 212", "carolina herrera", 59990, 200, "nueva fragancia", 13);
    }

    @Test
    void agregarStock_debeResponderConfirmacion() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        
        perfumeEjemplo.setStock(14);
         String jsonControlStock = "{ \"cantidad\": 14 }";  // o los campos necesarios

    mockMvc.perform(post("/api/v1/stock/agregar/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonControlStock))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock").value(14));
            }

    @Test
    void obtenerStock_debeRetornarStock() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);

        mockMvc.perform(get("/api/v1/perfumes/stock/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(13));
    }

    
    @Test
    void disminuirStock_debeResponderConfirmacion() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);

        perfumeEjemplo.setStock(12);
        when(perfumeService.updatePerfume(any(Perfumes.class))).thenReturn(perfumeEjemplo);

        mockMvc.perform(put("/api/v1/perfumes/stock/disminuir/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(12));
    }

    @Test
    void verStock_debeMostrarStockAgregados() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/stock/agregar/1"));

        mockMvc.perform(get("/api/v1/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePerfume").value("vip 212"));
    }

    @Test
    void eliminarStock_debeEliminarCorrectamente() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/stock/agregar/1"));

        mockMvc.perform(delete("/api/v1/stock/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock eliminado"));
    }

    @Test
    void vaciarStock_debeResponderCorrectamente() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/stock/agregar/1"));

        mockMvc.perform(delete("/api/v1/stock/vaciar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock vaciado"));
    }

    @Test
    void totalStock_debeRetornarCantidad() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/stock/agregar/1"));

        mockMvc.perform(get("/api/v1/stock/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

}


