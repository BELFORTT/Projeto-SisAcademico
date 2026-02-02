package br.edu.ifpb.sisacademico;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.Disciplina;
import br.edu.ifpb.sisacademico.service.AlunoService;

public class Main {
    public static void main(String[] args) {
        AlunoService service = new AlunoService();
        Disciplina disciplina = new Disciplina();

        try {
            // Teste 1: Cadastrando aluno corretamente
            service.cadastrarAluno("2023001", "Maria");
            
            // Teste 2: Tentando cadastrar o MESMO aluno (vai dar erro)
            service.cadastrarAluno("2023001", "João"); 

        } catch (SisAcademicoException e) {
            // Captura o erro do Service
            System.out.println("ERRO DE NEGÓCIO: " + e.getMessage());
        }

        System.out.println("-------------------");

        try {
            // Teste 3: Nota inválida
            disciplina.adicionarNota(15.0); // Vai explodir o erro
            
        } catch (SisAcademicoException e) {
            // Captura o erro do Model
            System.out.println("ERRO NA DISCIPLINA: " + e.getMessage());
        }
    }
}