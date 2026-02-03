package br.edu.ifpb.sisacademico.service;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Professor;
import br.edu.ifpb.sisacademico.repository.ProfessorRepository;
import java.util.List;

public class ProfessorService {
    private ProfessorRepository repository = new ProfessorRepository();

    public void cadastrarProfessor(String matricula, String nome) {
        if (repository.buscarPorMatricula(matricula) != null) {
            throw new SisAcademicoException("Professor já cadastrado com matrícula: " + matricula);
        }
        Professor prof = new Professor(matricula, nome);
        repository.salvar(prof);
        System.out.println("Professor cadastrado: " + nome);
    }
    
    // Método auxiliar para usar na criação de disciplinas
    public Professor buscarProfessor(String matricula) {
        return repository.buscarPorMatricula(matricula);
    }
    public List<Professor> listarProfessores() {
        return repository.listarTodos();
    }
}