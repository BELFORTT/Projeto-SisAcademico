package br.edu.ifpb.sisacademico.ui;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Aluno;
import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import br.edu.ifpb.sisacademico.model.Professor;
import br.edu.ifpb.sisacademico.service.AlunoService;
import br.edu.ifpb.sisacademico.service.ComponenteService;
import br.edu.ifpb.sisacademico.service.MatriculaService;
import br.edu.ifpb.sisacademico.service.ProfessorService;

import java.util.Arrays;
import java.util.Scanner;

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
                "Listar Alunos [EXTRA]",           // <--- NOVO
                "Listar Professores [EXTRA]",      // <--- NOVO
                "Listar Componentes (UC11)",       // Já inclui Disciplinas
                "Registrar Nota (UC07) [BELFORT]",
                "Registrar Avaliação Estágio (UC08) [BELFORT]",
                "Calcular Resultados (UC09) [BELFORT]"
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
            case 7 -> listarAlunos();              // <--- NOVO
            case 8 -> listarProfessores();         // <--- NOVO
            case 9 -> compService.listarComponentes();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Funcionalidade do Colega (Belfort) ou Inválida.");
        }
    }

    
    private void cadastrarAluno() {
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        alunoService.cadastrarAluno(mat, nome);
        System.out.println(Cores.VERDE + "Aluno cadastrado!" + Cores.RESET);
    }

    private void cadastrarProfessor() {
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        profService.cadastrarProfessor(mat, nome);
        System.out.println(Cores.VERDE + "Professor cadastrado!" + Cores.RESET);
    }

    private void cadastrarDisciplina() {
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
    
    private void listarAlunos() {
        System.out.println("\n--- LISTA DE ALUNOS ---");
        var alunos = alunoService.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno a : alunos) {
                System.out.println("Matrícula: " + a.getMatricula() + " | Nome: " + a.getNome());
            }
        }
    }

    private void listarProfessores() {
        System.out.println("\n--- LISTA DE PROFESSORES ---");
        var profs = profService.listarProfessores(); // Certifique-se de criar este método no Service
        if (profs.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            for (Professor p : profs) {
                System.out.println("Matrícula: " + p.getMatricula() + " | Nome: " + p.getNome());
            }
        }
    }
}