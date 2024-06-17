package com.forumhub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(
        String titulo,
        String mensagem,
        String curso

) {
}
