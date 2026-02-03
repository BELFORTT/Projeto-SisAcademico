package br.edu.ifpb.sisacademico.model;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class Matricula {
    private Aluno aluno;
    private ComponenteCurricular componente;
    private List<Double> notas = new ArrayList<>();
    private double notaEstagio = 0.0;

    public Matricula(Aluno aluno, ComponenteCurricular componente) {
        this.aluno = aluno;
        this.componente = componente;
    }

    public double getMedia() {
        if (componente instanceof Disciplina) {
            if (notas.isEmpty()) {
                return 0.0;
            } 

            double soma = 0;
            for (Double nota : notas) {
                soma += nota;
            }

            return soma / notas.size();
        }

        else {
            return notaEstagio;
        }
    }

    public String getSituacao() {
        return getMedia() >= 7.0 ? "APROVADO" : "REPROVADO";
    }

    public void adicionarNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new SisAcademicoException("Nota Inválida");
        }
        this.notas.add(nota);
    }
}
