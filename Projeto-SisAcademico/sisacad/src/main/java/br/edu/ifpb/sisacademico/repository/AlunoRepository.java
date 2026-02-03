package br.edu.ifpb.sisacademico.repository;

import br.edu.ifpb.sisacademico.model.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private List<Aluno> bancoDeAlunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        bancoDeAlunos.add(aluno);
    }

    public List<Aluno> listarTodos() {
        return bancoDeAlunos;
    }
    

    // Retorna Aluno ou null
    public Aluno buscarPorMatricula(String matricula) {
        for (Aluno aluno : bancoDeAlunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno; // Achou e devolve o objeto
            }
        }
        return null; // Percorreu tudo e não achou ninguém
    }
}