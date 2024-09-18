package com.application.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.entity.Rol;
import com.application.entity.Usuario;
import com.application.repository.UsuarioRepository;
import com.application.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Usuario crearUsuario(Usuario usuario) {

        Set<Rol> rols = new HashSet<>();

        Usuario usu = Usuario.builder()
        .username(usuario.getUsername())
        .password(encoder.encode(usuario.getPassword()))
        .roles(usuario.getRoles())
        .build();

        return usuarioRepository.save(usu);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usu = usuarioRepository.findUsuarioByUsername(username);

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        usu.getRoles()
        .forEach(role -> roles.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        return new User(usu.getUsername(), usu.getPassword(), roles);
    }

}
