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

            while (rs.next()) {
                listaAlunos.add(new Aluno(rs.getString("matricula"), rs.getString("nome")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar alunos", e);
        }

        return listaAlunos;
    }

    public void atualizar(String matriculav, String novoNome) {
        String updateSql = "UPDATE alunos SET nome = ? WHERE matricula = ?";

        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, novoNome);
            preparedStatement.setString(2, matriculav);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum aluno encontrado com a matrícula: " + matriculav);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aluno", e);
        }
    }

    public void deletar(String matriculav) {
        String deleteSql = "DELETE FROM alunos WHERE matricula = ?";

        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setString(1, matriculav);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum aluno encontrado com a matrícula: " + matriculav);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar aluno", e);
        }
    }

    public Aluno buscarPorMatricula(String matriculav) {
        String selectSql = "SELECT matricula, nome FROM alunos WHERE matricula = ?";
        Aluno alunoEncontrado = null; // Começa vazio

        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            preparedStatement.setString(1, matriculav);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String matriculaBanco = rs.getString("matricula");
                    String nomeBanco = rs.getString("nome");

                    alunoEncontrado = new Aluno(matriculaBanco, nomeBanco);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aluno por matrícula", e);
        }

        return alunoEncontrado;
    }
}
