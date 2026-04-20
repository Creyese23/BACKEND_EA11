package com.example.productos.service;

import com.example.productos.dto.LoginRequest;
import com.example.productos.dto.LoginResponse;
import com.example.productos.model.Usuario;
import com.example.productos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        // Buscar usuario en la base de datos por email
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(request.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar contraseña usando BCrypt
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Generar token JWT con el email del usuario
        String token = jwtUtil.generateJwtToken(usuario.getEmail());
        
        return new LoginResponse(token);
    }
}
