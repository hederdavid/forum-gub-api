package com.forumhub.api.domain.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @Email
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
