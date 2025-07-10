package com.fullstack.perfulandiaSPA.Service;
import com.fullstack.perfulandiaSPA.Model.ControlStock;
import com.fullstack.perfulandiaSPA.Repository.controlstockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ControlStockService {
     @Autowired
    private controlstockRepository controlRepo;

    public List<ControlStock> getControlStocks(){
        return controlRepo.obtenerStocks();
    }

    public ControlStock saveControlStock(ControlStock controlStock){
        return controlRepo.guardar(controlStock);
    }

    public ControlStock getControlStocks(int id){
        return controlRepo.buscarPorId(id);
    }

    public ControlStock updateControlStock(ControlStock controlStock){
        return controlRepo.actualizar(controlStock);
    }

    public String deleteControl(int id){
        controlRepo.eliminar(id);
        return "Control de stock eliminado";
    }

    public int totalControles(){
        return controlRepo.obtenerStocks().size();
    }

    public int totalControlesV2(){
        return controlRepo.totalControles();
    }
}



