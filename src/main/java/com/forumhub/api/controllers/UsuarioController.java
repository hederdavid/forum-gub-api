package com.forumhub.api.controllers;

import com.forumhub.api.domain.usuario.DadosAutenticacao;
import com.forumhub.api.domain.usuario.Usuario;
import com.forumhub.api.infra.security.DadosTokenJWT;
import com.forumhub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        System.out.println("passou aqui");
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        System.out.println(authToken);
        var authentication = manager.authenticate(authToken);
        System.out.println(authentication);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        System.out.println(tokenJWT);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
