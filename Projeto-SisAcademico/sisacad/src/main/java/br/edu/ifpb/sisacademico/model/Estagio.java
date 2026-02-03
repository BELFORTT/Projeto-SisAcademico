package br.edu.ifpb.sisacademico.model;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estagio extends ComponenteCurricular {
    private double notaFinal;

    @Override
    public double calcularMediaFinal() {
        if (notaFinal < 0 || notaFinal > 100) {
            throw new SisAcademicoException("A nota deve ser entre 0 e 10. Valor inválido: " + notaFinal);
        }
        return this.notaFinal;
    }
}
