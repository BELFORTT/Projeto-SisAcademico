package br.edu.ifpb.sisacademico.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Aluno;
import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import br.edu.ifpb.sisacademico.model.Matricula;
import br.edu.ifpb.sisacademico.repository.MatriculaRepository;

public class MatriculaService {
    private MatriculaRepository matriculaRepository = new MatriculaRepository();
    // Serve para buscar os objetos antes de vincular
    private AlunoService alunoService; 
    private ComponenteService componenteService;
    // Construtor recebe os outros services
    public MatriculaService(AlunoService alunoService, ComponenteService componenteService) {
        this.alunoService = alunoService;
        this.componenteService = componenteService;
    }

    // Serve tanto para UC05 quanto UC06
    public void matricular(String matriculaAluno, String nomeComponente) {
        // 1. Busca os objetos reais
        
        if (matriculaRepository.existeMatricula(matriculaAluno, nomeComponente)) {
            throw new SisAcademicoException("Aluno já matriculado neste componente.");
        }
    }
    
    public void realizarMatricula(Aluno aluno, ComponenteCurricular componente) {
         if (matriculaRepository.existeMatricula(aluno.getMatricula(), componente.getNome())) {
            throw new SisAcademicoException("Aluno já matriculado em: " + componente.getNome());
        }
         
         Matricula novaMatricula = new Matricula(aluno, componente);
         matriculaRepository.salvar(novaMatricula);
         System.out.println("Matrícula realizada: " + aluno.getNome() + " -> " + componente.getNome());
    }

    public List<Matricula> buscarMatriculasDoAluno(String matriculaAluno) {
    List<Matricula> resultado = new ArrayList<>();
    
    for (Matricula m : matriculaRepository.listarTodas()) {
        if (m.getAluno().getMatricula().equals(matriculaAluno)) {
            resultado.add(m);
        }
    }
    return resultado;
}

    public List<Matricula> listarTodasMatriculas() {
        return matriculaRepository.listarTodas();
    }
}