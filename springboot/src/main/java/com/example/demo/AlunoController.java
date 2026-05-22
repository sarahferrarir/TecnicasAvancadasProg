package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Gerenciamento de Alunos", description = "Endpoints para criar e listar alunos")
public class AlunoController {

    private final GerenciadorAlunos gerenciadorAlunos;

    // Injeção de dependência do Spring
    public AlunoController(GerenciadorAlunos gerenciadorAlunos) {
        this.gerenciadorAlunos = gerenciadorAlunos;
    }

    @PostMapping
    @Operation(summary = "Inserir novo aluno", description = "Adiciona um novo aluno ao banco de dados MySQL.")
    public String inserirAluno(
            @RequestParam String matricula,
            @RequestParam String nome) {
        gerenciadorAlunos.insercao(matricula, nome);
        return "Aluno inserido com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar alunos", description = "Retorna uma lista de todos os alunos cadastrados no banco.")
    public List<Aluno> listarAlunos() {
        return gerenciadorAlunos.resultado();
    }
}