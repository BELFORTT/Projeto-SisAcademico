package br.edu.ifpb.sisacademico.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Aluno;
import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import br.edu.ifpb.sisacademico.model.Disciplina;
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

    public void registrarDesempenho(Matricula matricula, double valor) {
        if (matricula.getComponente() instanceof Disciplina) {
            matricula.adicionarNota(valor);
        } else {
            matricula.setNotaEstagio(valor);
        }

        System.out.println("Desempenho registrado para: " + matricula.getAluno().getNome());
    }

    public List<String> gerarBoletimAluno(String matriculaAluno) {
        List<Matricula> matriculas = buscarMatriculasDoAluno(matriculaAluno);
        List<String> boletim = new ArrayList<>();

        for (Matricula m : matriculas) {
            String linha = String.format("Componente: %-15s | Média: %5.2f | Situação: %s",
                m.getComponente().getNome(),
                m.getMedia(),
                m.getSituacao());
            boletim.add(linha);
        }
        return boletim;
    }

    public List<String> obterDetalhesAvaliacao(String matriculaAluno) {
    List<Matricula> matriculas = buscarMatriculasDoAluno(matriculaAluno);
    List<String> detalhes = new ArrayList<>();

    for (Matricula m : matriculas) {
        StringBuilder sb = new StringBuilder();
        sb.append("Componente: ").append(m.getComponente().getNome()).append("\n");
        
        if (m.getComponente() instanceof Disciplina) {
            sb.append("  > Notas: ").append(m.getNotas()).append("\n");
        } else {
            sb.append("  > Estágio\n");
        }
        
        sb.append("  > Média: ").append(String.format("%.2f", m.getMedia())).append("\n");
        sb.append("  > Situação: ").append(m.getSituacao()).append("\n");
        
        detalhes.add(sb.toString());
    }
    return detalhes;
    }

    public List<String> consultarTodosAlunos() {
    List<Matricula> todas = matriculaRepository.listarTodas();
    List<String> relatorio = new ArrayList<>();

    for (Matricula m : todas) {
        String info = String.format("Aluno: %-15s | Comp: %-15s | Situação: %s",
            m.getAluno().getNome(),
            m.getComponente().getNome(),
            m.getSituacao());
        relatorio.add(info);
    }
    return relatorio;
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

