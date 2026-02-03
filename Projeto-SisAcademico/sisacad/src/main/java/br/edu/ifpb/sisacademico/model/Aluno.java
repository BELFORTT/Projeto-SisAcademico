package br.edu.ifpb.sisacademico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Aluno {
    private String matricula;
    private String nome;
    // O colega irá implementar a lista de matrículas/vínculos depois
}