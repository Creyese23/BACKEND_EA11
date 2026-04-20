package com.example.productos.service;

import com.example.productos.exception.RecursoNoEncontradoException;
import com.example.productos.model.Usuario;
import com.example.productos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con ID " + id + " no existe"));
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        
        // Hashear la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario existente = obtenerPorId(id);

        // Verificar si el email ya está siendo usado por otro usuario
        if (!existente.getEmail().equals(usuario.getEmail()) && existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }

        if (usuario.getNombre() != null)
            existente.setNombre(usuario.getNombre());

        if (usuario.getEmail() != null)
            existente.setEmail(usuario.getEmail());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty())
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        repository.delete(usuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
