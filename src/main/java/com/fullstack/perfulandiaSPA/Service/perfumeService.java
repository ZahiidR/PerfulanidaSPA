package com.fullstack.perfulandiaSPA.Service;

import com.fullstack.perfulandiaSPA.Model.Perfumes;
import com.fullstack.perfulandiaSPA.Repository.PerfumesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class perfumeService {
    @Autowired
    private PerfumesRepository perfumeRepo;

    public List<Perfumes> getPerfumes(){
        return perfumeRepo.obtenerPefumes();
    }

    public Perfumes savePerfume(Perfumes perfume){
        return perfumeRepo.guardar(perfume);
    }

    public Perfumes getPerfumeId(int id){
        return perfumeRepo.buscarPorId(id);
    }

    public Perfumes updatePerfume(Perfumes perfume){
        return perfumeRepo.actualizar(perfume);
    }

    public String deletePerfume(int id){
        perfumeRepo.eliminar(id);
        return "Producto eliminado";
    }

    public int totalPerfumes(){
        return perfumeRepo.obtenerPefumes().size();
    }

    public int totalPerfumesv2(){
        return perfumeRepo.totalPerfumes();
    }
}

