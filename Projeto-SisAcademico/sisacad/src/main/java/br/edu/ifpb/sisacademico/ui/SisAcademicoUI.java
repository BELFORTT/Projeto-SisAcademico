package br.edu.ifpb.sisacademico.ui;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Aluno;
import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import br.edu.ifpb.sisacademico.model.Matricula;
import br.edu.ifpb.sisacademico.model.Disciplina;
import br.edu.ifpb.sisacademico.model.Professor;
import br.edu.ifpb.sisacademico.service.AlunoService;
import br.edu.ifpb.sisacademico.service.ComponenteService;
import br.edu.ifpb.sisacademico.service.MatriculaService;
import br.edu.ifpb.sisacademico.service.ProfessorService;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class SisAcademicoUI {
    private Scanner scanner = new Scanner(System.in);
    
    // Dependências (Services)
    private AlunoService alunoService = new AlunoService();
    private ProfessorService profService = new ProfessorService();
    private ComponenteService compService = new ComponenteService();
    private MatriculaService matService = new MatriculaService(alunoService, compService);

    // O Objeto Menu
    private Menu menuPrincipal;

    public SisAcademicoUI() {
        this.menuPrincipal = new Menu(
            "SISACADÊMICO IFPB", 
            Arrays.asList(
                "Cadastrar Aluno (UC01)",
                "Cadastrar Professor (UC02)",
                "Cadastrar Disciplina (UC03)",
                "Cadastrar Estágio (UC04)",
                "Matricular em Disciplina (UC05)",
                "Matricular em Estágio (UC06)",
                "Registrar Nota (UC07)",
                "Registrar avaliação em Estágio (UC08)",
                "Calcular resultado de aluno em componentes acadêmicos (UC09)",
                "Consultar situação de aluno específico (UC10)",     
                "Listar Componentes (UC11)", // Já inclui Disciplinas
                "Solicitar consulta de detalhes de avaliação para aluno/disciplina (UC12)",
                "Solicitar consulta sobre todos os alunos matriculados (UC13)",   
                "Listar Professores"
            ), 
            "Escolha uma opção: ", 
            scanner
        );
    }

    public void executar() {
        int opcao;
        do {
            menuPrincipal.exiba();
            opcao = menuPrincipal.leiaOpcao();
            
            try {
                processarOpcao(opcao);
            } catch (SisAcademicoException e) {
                System.out.println(Cores.VERMELHO + "ERRO: " + e.getMessage() + Cores.RESET);
            } catch (Exception e) {
                System.out.println(Cores.VERMELHO + "ERRO INESPERADO: " + e.getMessage() + Cores.RESET);
            }
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarAluno();
            case 2 -> cadastrarProfessor();
            case 3 -> cadastrarDisciplina();
            case 4 -> cadastrarEstagio();
            case 5 -> matricularAluno();
            case 6 -> matricularAluno();    
            case 7 -> registrarDesempenho();
            case 8 -> registrarDesempenho();
            case 9 -> consultarSituacaoAluno();
            case 10 -> consultarSituacaoAluno();
            case 11 -> compService.listarComponentes();
            case 12 -> consultarDetalhesAvaliacao();
            case 13 -> verTodosAlunos();              
            case 14 -> listarProfessores();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opcao inválida");
        }
    }

    
    private void cadastrarAluno() {
        System.out.println("\n--- Lista Atual de Alunos ---");
        verTodosAlunos(); // <--- CHAMA A LISTAGEM DOS ALUNOS
        System.out.println("-----------------------------");
        
        System.out.println(">> NOVO CADASTRO:");
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        alunoService.cadastrarAluno(mat, nome);
        System.out.println(Cores.VERDE + "Aluno cadastrado!" + Cores.RESET);
    }

    private void cadastrarProfessor() {
        System.out.println("\n--- Lista Atual de Professores ---");
        listarProfessores(); // <--- CHAMA A LISTAGEM DOS PROF
        System.out.println("----------------------------------");

        System.out.println(">> NOVO CADASTRO:");
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        profService.cadastrarProfessor(mat, nome);
        System.out.println(Cores.VERDE + "Professor cadastrado!" + Cores.RESET);
    }

    private void cadastrarDisciplina() {
        // Mostra os professores para o usuário escolher o responsável
        System.out.println("\n--- Professores Disponíveis ---");
        listarProfessores();

        System.out.println("-------------------------------");
        System.out.print("Nome Disciplina: ");
        String nome = scanner.nextLine();
        System.out.print("Carga Horária: ");
        int ch = Integer.parseInt(scanner.nextLine());
        System.out.print("Matrícula Prof: ");
        String matProf = scanner.nextLine();
        
        Professor p = profService.buscarProfessor(matProf);
        if (p == null) throw new SisAcademicoException("Professor não achado!");
        
        compService.cadastrarDisciplina(nome, ch, p);
        System.out.println(Cores.VERDE + "Disciplina cadastrada!" + Cores.RESET);
    }

    private void cadastrarEstagio() {
        System.out.print("Nome Estágio: ");
        String nome = scanner.nextLine();
        System.out.print("Carga Horária: ");
        int ch = Integer.parseInt(scanner.nextLine());
        System.out.print("Matrícula Prof Supervisor: ");
        String matProf = scanner.nextLine();

        Professor p = profService.buscarProfessor(matProf);
        if (p == null) throw new SisAcademicoException("Professor não achado!");

        compService.cadastrarEstagio(nome, ch, p);
        System.out.println(Cores.VERDE + "Estágio cadastrado!" + Cores.RESET);
    }

    private void matricularAluno() {
        // Ajuda o usuário mostrando as opções
        System.out.println("\n--- Alunos ---");
        verTodosAlunos();
        System.out.println("\n--- Componentes ---");
        compService.listarComponentes();
        System.out.println("-------------------");


        System.out.print("Matrícula Aluno: ");
        String mat = scanner.nextLine();
        System.out.print("Nome Componente: ");
        String comp = scanner.nextLine();

        Aluno a = alunoService.buscarAluno(mat);
        ComponenteCurricular c = compService.buscarPeloNome(comp);

        if (a == null || c == null) throw new SisAcademicoException("Dados inválidos.");

        matService.realizarMatricula(a, c);
        System.out.println(Cores.VERDE + "Matrícula realizada!" + Cores.RESET);
    }
    
    private void registrarDesempenho() {
        System.out.print("Matrícula do Aluno: ");
        String mat = scanner.nextLine();
        List<Matricula> matriculas = matService.buscarMatriculasDoAluno(mat);

        if (matriculas.isEmpty()) {
            throw new SisAcademicoException("Nenhuma matrícula encontrada");
        }

        System.out.println("Selecione o componente: ");
        for (int i = 0; i < matriculas.size(); i++) {
            System.out.println(i + " - " + matriculas.get(i).getComponente().getNome());
        }
        int escolha = Integer.parseInt(scanner.nextLine());
        Matricula m = matriculas.get(escolha);

        System.out.print("Informe a nota/avaliação: ");
        double valor = Double.parseDouble(scanner.nextLine());

        matService.registrarDesempenho(m, valor);

        System.out.println(Cores.VERDE + "Nota registrada!" + Cores.RESET);
    }

    private void consultarSituacaoAluno() {
        System.out.print("Matrícula do Aluno: ");
        String mat = scanner.nextLine();
        
        List<String> linhasBoletim = matService.gerarBoletimAluno(mat);

        if (linhasBoletim.isEmpty()) {
            System.out.println(Cores.AMARELO + "Nenhuma matrícula encontrada." + Cores.RESET);
            return;
        }

        System.out.println("\n" + Cores.AZUL + "--- BOLETIM ACADÊMICO ---" + Cores.RESET);
        for (String linha : linhasBoletim) {
            if (linha.contains("APROVADO")) {
                System.out.println(Cores.VERDE + linha + Cores.RESET);
            } else {
                System.out.println(Cores.VERMELHO + linha + Cores.RESET);
            }
        }
    }

    private void consultarDetalhesAvaliacao() {
        System.out.print("Matrícula do Aluno: ");
        String mat = scanner.nextLine();
        
        List<String> relatorioDetalhado = matService.obterDetalhesAvaliacao(mat);

        if (relatorioDetalhado.isEmpty()) {
            System.out.println(Cores.AMARELO + "Sem informações para este aluno." + Cores.RESET);
            return;
        }

        System.out.println("\n--- DETALHES DE AVALIAÇÃO ---");
        for (String detalhe : relatorioDetalhado) {
            System.out.println(detalhe);
            System.out.println("---------------------------");
        }
    }
    
    private void verTodosAlunos() {
        System.out.println("\n" + Cores.AZUL + "=== RELATÓRIO GERAL DE ALUNOS ===" + Cores.RESET);
        
        List<String> linhas = matService.consultarTodosAlunos();

        if (linhas.isEmpty()) {
            System.out.println("Nenhuma matrícula registrada no sistema.");
            return;
        }

        for (String linha : linhas) {
            System.out.println(linha);
        }
    }

    private void listarProfessores() {
        System.out.println("\n--- LISTA DE PROFESSORES ---");
        var profs = profService.listarProfessores(); 
        if (profs.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            for (Professor p : profs) {
                System.out.println("Matrícula: " + p.getMatricula() + " | Nome: " + p.getNome());
            }
        }
    }
}
