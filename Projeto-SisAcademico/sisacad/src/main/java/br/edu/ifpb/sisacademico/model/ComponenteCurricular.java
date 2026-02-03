package br.edu.ifpb.sisacademico.model;

import lombok.Data;

@Data
public abstract class ComponenteCurricular {
    private String nome;
    private int cargaHoraria;
    private Professor professorResponsavel;

    // Método abstrato: cada filho decide como calcular seu resultado
    // Requisito: "formas diferentes... adotar regras próprias" [cite: 12, 13]
    public abstract double calcularMediaFinal();
}