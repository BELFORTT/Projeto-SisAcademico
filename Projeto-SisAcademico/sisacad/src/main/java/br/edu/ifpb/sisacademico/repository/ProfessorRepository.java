package br.edu.ifpb.sisacademico.repository;

import br.edu.ifpb.sisacademico.model.Professor;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {
    private List<Professor> banco = new ArrayList<>();

    public void salvar(Professor professor) {
        banco.add(professor);
    }

    // Lambda para busca (Requisito)
    public Professor buscarPorMatricula(String matricula) {
        return banco.stream()
                .filter(p -> p.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }
    public List<Professor> listarTodos() {
        return banco;
    }
}