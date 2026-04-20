package com.example.productos.service;

import com.example.productos.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario obtenerPorId(Long id);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Long id, Usuario usuario);
    void eliminar(Long id);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
