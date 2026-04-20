package com.example.productos.service;

import com.example.productos.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listar();
    Producto obtenerPorId(Long id);
    Producto guardar(Producto producto);
    Producto actualizar(Long id, Producto producto);
    void eliminar(Long id);
}