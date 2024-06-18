package com.forumhub.api.controllers;

import com.forumhub.api.domain.usuario.DadosAutenticacao;
import com.forumhub.api.domain.usuario.Usuario;
import com.forumhub.api.domain.usuario.UsuarioRepository;
import com.forumhub.api.infra.security.DadosTokenJWT;
import com.forumhub.api.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/singin")
    @Transactional
    public void criarConta(@RequestBody @Valid DadosAutenticacao dados) {
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        repository.save(new Usuario(dados.login(), senhaCriptografada));
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
