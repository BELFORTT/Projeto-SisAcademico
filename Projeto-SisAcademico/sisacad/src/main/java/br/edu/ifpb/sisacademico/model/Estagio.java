package br.edu.ifpb.sisacademico.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estagio extends ComponenteCurricular {
    private double notaFinal;

    @Override
    public double calcularMediaFinal() {
        return this.notaFinal;
    }
}
