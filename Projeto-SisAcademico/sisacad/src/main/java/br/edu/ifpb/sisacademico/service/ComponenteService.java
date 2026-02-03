package br.edu.ifpb.sisacademico.service;

import br.edu.ifpb.sisacademico.exception.SisAcademicoException;
import br.edu.ifpb.sisacademico.model.ComponenteCurricular;
import br.edu.ifpb.sisacademico.model.Disciplina;
import br.edu.ifpb.sisacademico.model.Estagio;
import br.edu.ifpb.sisacademico.model.Professor;
import br.edu.ifpb.sisacademico.repository.ComponenteRepository;

public class ComponenteService {
    private ComponenteRepository repository = new ComponenteRepository();

    // UC03: Cadastrar Disciplina
    public void cadastrarDisciplina(String nome, int cargaHoraria, Professor professor) {
        validarComponenteExistente(nome);
        
        Disciplina d = new Disciplina();
        d.setNome(nome);
        d.setCargaHoraria(cargaHoraria);
        d.setProfessorResponsavel(professor);
        
        repository.salvar(d);
        System.out.println("Disciplina cadastrada: " + nome);
    }

    // UC04: Cadastrar Estágio
    public void cadastrarEstagio(String nome, int cargaHoraria, Professor professor) {
        validarComponenteExistente(nome);

        Estagio e = new Estagio();
        e.setNome(nome);
        e.setCargaHoraria(cargaHoraria);
        e.setProfessorResponsavel(professor);
        
        repository.salvar(e);
        System.out.println("Estágio cadastrado: " + nome);
    }

    // UC11: Listar Componentes
    public void listarComponentes() {
        System.out.println("--- Lista de Componentes ---");
        repository.listarTodos().forEach(c -> 
            System.out.println(c.getNome() + " | Prof: " + c.getProfessorResponsavel().getNome())
        );
    }
    
    public ComponenteCurricular buscarPeloNome(String nome) {
        return repository.buscarPorNome(nome);
    }

    private void validarComponenteExistente(String nome) {
        if (repository.buscarPorNome(nome) != null) {
            throw new SisAcademicoException("Componente já existe: " + nome);
        }
    }
}