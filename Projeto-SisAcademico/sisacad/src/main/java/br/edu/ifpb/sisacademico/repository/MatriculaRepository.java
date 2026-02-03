package br.edu.ifpb.sisacademico.repository;

import br.edu.ifpb.sisacademico.model.Matricula;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepository {
    private List<Matricula> banco = new ArrayList<>();

    public void salvar(Matricula matricula) {
        banco.add(matricula);
    }
    
    // Verifica se o aluno já está matriculado naquele componente
    public boolean existeMatricula(String matriculaAluno, String nomeComponente) {
        return banco.stream()
                .anyMatch(m -> m.getAluno().getMatricula().equals(matriculaAluno) 
                            && m.getComponente().getNome().equalsIgnoreCase(nomeComponente));
    }

    public List<Matricula> listarTodas() {
        return banco;
    }
}