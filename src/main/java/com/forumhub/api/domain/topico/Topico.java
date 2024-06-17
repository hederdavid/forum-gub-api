package com.forumhub.api.domain.topico;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean status;
    private String autor;
    private String curso;

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
        this.autor = dados.autor();
        this.curso = dados.curso();
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }

        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }

        if (dados.curso() != null) {
            this.curso = dados.curso();
        }
    }

    public void deletar() {
        this.status = false;
    }
}
