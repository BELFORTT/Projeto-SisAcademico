package br.edu.ifpb.sisacademico.service;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Aluno;
import br.edu.ifpb.sisacademico.repository.AlunoRepository;

public class AlunoService {
    private AlunoRepository repository = new AlunoRepository();

    public void cadastrarAluno(String matricula, String nome) {
        Aluno alunoEncontrado = repository.buscarPorMatricula(matricula);
        
        // Verifica se já existe
        if (alunoEncontrado != null) {
            throw new SisAcademicoException("Já existe um aluno com a matrícula " + matricula);
        }
        
        // Se passou, cadastra
        Aluno novo = new Aluno(matricula, nome);
        repository.salvar(novo);
        System.out.println("Aluno cadastrado com sucesso: " + nome);
        
    }
    public Aluno buscarAluno(String matricula) {
        return repository.buscarPorMatricula(matricula);
    }
    public java.util.List<Aluno> listarAlunos() {
        return repository.listarTodos();
    }
}