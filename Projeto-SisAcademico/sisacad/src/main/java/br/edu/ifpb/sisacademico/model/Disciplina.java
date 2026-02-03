package br.edu.ifpb.sisacademico.model;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Disciplina extends ComponenteCurricular {
    private List<Double> notas = new ArrayList<>();

    @Override
    public double calcularMediaFinal() {
        if (notas.isEmpty()) return 0.0;
        double soma = notas.stream().mapToDouble(Double::doubleValue).sum();
        return soma / notas.size();
    }
    
    // Tratamento de erro
    public void adicionarNota(double nota) {
        if (nota < 0 || nota > 10) {
            // disparamos o erro
            throw new SisAcademicoException("A nota deve ser entre 0 e 10. Valor inválido: " + nota);
        }
        this.notas.add(nota);
    }
}