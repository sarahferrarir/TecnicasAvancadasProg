package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Gerenciamento de Alunos", description = "")
public class AlunoController {

    private final GerenciadorAlunos gerenciadorAlunos;

    // Injeção de dependência do Spring
    public AlunoController(GerenciadorAlunos gerenciadorAlunos) {
        this.gerenciadorAlunos = gerenciadorAlunos;
    }

    @PostMapping
    @Operation(summary = "Inserir novo aluno", description = "Adiciona um novo aluno ao banco.")
    public void inserirAluno(
            @RequestParam String matricula,
            @RequestParam String nome) {
        gerenciadorAlunos.insercao(matricula, nome);
    }

    @GetMapping
    @Operation(summary = "Listar alunos", description = "Retorna uma lista de todos os alunos cadastrados no banco.")
    public List<Aluno> listarAlunos() {
        return gerenciadorAlunos.resultado();
    }

    @GetMapping("/{matricula}")
    @Operation(summary = "Buscar aluno por matrícula", description = "Retorna os detalhes de um aluno específico.")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable String matricula) {

        Aluno aluno = gerenciadorAlunos.buscarPorMatricula(matricula);

        if (aluno != null) {
            return ResponseEntity.ok(aluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Deletar aluno", description = "Deleta um aluno do banco.")
    public void deletarAluno(
            @RequestParam String matricula) {
        gerenciadorAlunos.deletar(matricula);
    }

    @PutMapping
    @Operation(summary = "Atualizar aluno", description = "Atualiza o nome de um aluno")
    public void atualizarAluno(
            @RequestParam String matricula,
            @RequestParam String novoNome){
        gerenciadorAlunos.atualizar(matricula,novoNome);
    }


}
