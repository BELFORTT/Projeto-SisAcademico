package br.edu.ifpb.sisacademico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Professor {
    private String matricula;
    private String nome;
}