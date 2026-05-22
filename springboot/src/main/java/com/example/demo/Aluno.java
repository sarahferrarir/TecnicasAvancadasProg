package com.example.demo;

public record Aluno(String matricula, String nome) {
    // Não precisa de construtor, getters ou setters. O Java faz tudo sozinho!
}
