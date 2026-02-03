package br.edu.ifpb.sisacademico.repository;

import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import java.util.ArrayList;
import java.util.List;

public class ComponenteRepository {
    private List<ComponenteCurricular> banco = new ArrayList<>();

    public void salvar(ComponenteCurricular componente) {
        banco.add(componente);
    }

    public List<ComponenteCurricular> listarTodos() {
        return banco;
    }

    public ComponenteCurricular buscarPorNome(String nome) {
        return banco.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
}