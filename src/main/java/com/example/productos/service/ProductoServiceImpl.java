package com.example.productos.service;

import com.example.productos.exception.RecursoNoEncontradoException;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Producto> listar() {
        return repository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no existe"));
    }
    @Override
    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        Producto existente = obtenerPorId(id);

        if (producto.getNombre() != null)
            existente.setNombre(producto.getNombre());

        if (producto.getDescripcion() != null)
            existente.setDescripcion(producto.getDescripcion());

        if (producto.getPrecio() != null)
            existente.setPrecio(producto.getPrecio());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        repository.delete(producto);
    }
}