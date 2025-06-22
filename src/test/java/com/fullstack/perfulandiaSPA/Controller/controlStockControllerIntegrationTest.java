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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
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
         String jsonControlStock = "{"
             + "\"perfume\": {\"id\": 1},"
             + "\"stockActual\": 14,"
             + "\"fechaActualizacion\": \"" + LocalDate.now().toString() + "\""
             + "}";

          ControlStock control = new ControlStock();
    control.setPerfume(perfumeEjemplo); // usar perfume como parte del objeto, como requiere tu modelo
    control.setStockActual(14);
    control.setFechaActualizacion(LocalDate.now().toString());

    when(controlStockService.saveControlStock(any(ControlStock.class))).thenReturn(control);
    


    mockMvc.perform(post("/api/v1/stock/agregar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonControlStock))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stockActual").value(14));
            }

    @Test
    void obtenerStock_debeRetornarStock() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
            ControlStock controlStock = new ControlStock(1, perfumeEjemplo, 13, "2025-06-21");
            
        when(controlStockService.getControlStocks(1)).thenReturn(controlStock);
        mockMvc.perform(get("/api/v1/stock/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockActual").value(13));
    }

    
   

    @Test
    void verStock_debeMostrarStockAgregados() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
          String jsonControlStock = "{"
        + "\"perfume\": {\"id\": 1},"
        + "\"stockActual\": 14,"
        + "\"fechaActualizacion\": \"2025-06-21\""
        + "}";

        when(controlStockService.saveControlStock(any(ControlStock.class)))
        .thenReturn(new ControlStock(1, perfumeEjemplo, 14, "2025-06-21"));

        mockMvc.perform(post("/api/v1/stock/agregar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonControlStock))
            .andExpect(status().isOk());

       when(controlStockService.getControlStocks())
    .thenReturn(List.of(new ControlStock(1, perfumeEjemplo, 14, "2025-06-21")));

        mockMvc.perform(get("/api/v1/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].perfume.nombrePerfume").value("vip 212"));
    }

    @Test
    void eliminarStock_debeEliminarCorrectamente() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(perfumeEjemplo);
        mockMvc.perform(post("/api/v1/stock/agregar"));

        when(controlStockService.deleteControl(1)).thenReturn("Control de stock eliminado");

        mockMvc.perform(delete("/api/v1/stock/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Control de stock eliminado"));
    }

   
@Test
void totalStock_debeRetornarCantidad() throws Exception {
    // Simulamos que el servicio ya tiene 1 control de stock
    when(controlStockService.totalControlesV2()).thenReturn(1);

    mockMvc.perform(get("/api/v1/stock/total"))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));
    }

}


