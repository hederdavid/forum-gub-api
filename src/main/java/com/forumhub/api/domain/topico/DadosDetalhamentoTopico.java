package com.forumhub.api.domain.topico;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosDetalhamentoTopico(
        UUID id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean status,
        String autor,
        String curso

) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(),
                topico.getAutor(), topico.getCurso());
    }
}
