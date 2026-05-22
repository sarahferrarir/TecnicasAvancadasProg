package com.example.demo;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service // Indica ao Spring que esta é uma classe de serviço
public class GerenciadorAlunos {

    String jdbcUrl = "jdbc:mysql://localhost:3306/alunos";
    String user = "root";
    String password = "admin";

    public Connection conexao() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    public void insercao(String matriculav, String nomev) {
        // Usando try-with-resources para garantir que a conexão será fechada
        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO alunos (matricula, nome) VALUES (?, ?)")) {

            preparedStatement.setString(1, matriculav);
            preparedStatement.setString(2, nomev);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir aluno", e);
        }
    }

    public List<Aluno> resultado() {
        List<Aluno> listaAlunos = new ArrayList<>();

        try (Connection connection = conexao();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT matricula, nome FROM alunos")) {

            // Preenche a lista com os resultados do banco
            while (rs.next()) {
                listaAlunos.add(new Aluno(rs.getString("matricula"), rs.getString("nome")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar alunos", e);
        }

        return listaAlunos; // Retorna os dados ao invés de imprimir
    }
}