package com.forumhub.api.controllers;

import com.forumhub.api.domain.topico.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(dados);
        repository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {
        var page = repository.findAllByStatusTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalherPeloId(@PathVariable UUID id) {
        var topico = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topico não encontrado."));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping("/curso/{nomeCurso}")
    public ResponseEntity<DadosDetalhamentoTopico> detalharPeloCurso(@PathVariable String nomeCurso) {
        var topico = repository.findByCurso(nomeCurso);
        if (topico == null) {
            throw new EntityNotFoundException("Topico do curso " + nomeCurso + " não encontrado");
        }
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Page<DadosListagemTopico>> detalharPeloAno(@PathVariable int ano, Pageable pageable) {
        var topicos = repository.findByAno(ano, pageable);
        var dadosListagemTopicoPage = topicos.map(DadosListagemTopico::new);
        return ResponseEntity.ok(dadosListagemTopicoPage);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable UUID id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topico não encontrado"));
        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity<Object> apagar(@PathVariable UUID id) {
        var topico = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topico não encontrado"));
        topico.deletar();
        return ResponseEntity.noContent().build();
    }
}
