package br.edu.ifpb.sisacademico.ui;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private String titulo;
    private List<String> itens;
    private String prompt; 
    private Scanner scanner;

    public Menu(String titulo, List<String> itens, String prompt, Scanner scanner) {
        this.titulo = titulo;
        this.itens = itens;
        this.prompt = prompt;
        this.scanner = scanner;
    }

    public void exiba() {
        System.out.println("\n" + Cores.CIANO + "=== " + titulo + " ===" + Cores.RESET);
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + ". " + itens.get(i));
        }
        System.out.println("0. Sair");
    }

    public int leiaOpcao() {
        System.out.print(Cores.AMARELO + prompt + Cores.RESET);
        while (!scanner.hasNextInt()) {
            System.out.print(Cores.VERMELHO + "Entrada inválida. " + prompt + Cores.RESET);
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        return opcao;
    }
}