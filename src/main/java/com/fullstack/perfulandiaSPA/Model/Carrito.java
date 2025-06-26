package com.fullstack.perfulandiaSPA.Model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Perfumes> perfumes;

    public Carrito() {
        this.perfumes = new ArrayList<>();
    }

    public List<Perfumes> getPerfumes() {
        return perfumes;
    }

    public void agregarPerfume(Perfumes perfume) {
        this.perfumes.add(perfume);
    }

    public List<Perfumes> verCarrito() {
        return this.perfumes;
    }

    public boolean eliminarPerfume(int id) {
        return this.perfumes.removeIf(perfume -> perfume.getId() == id);
    }

    public void vaciar() {
        this.perfumes.clear();
    }

    public int totalPerfumes() {
        return this.perfumes.size();
    }
}
