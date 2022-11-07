package com.mechanical.mechanicalsite.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.mechanical.mechanicalsite.model.Peca;
import com.mechanical.mechanicalsite.model.exception.ResourceNotFoundException;

@Repository
public class PecaRepositoryMock {
    private ArrayList<Peca> pecas = new ArrayList<Peca>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de pecas
     * 
     * @return Lista de pecas
     */
    public ArrayList<Peca> obterTodos() {
        return pecas;
    }

    /**
     * Optional serve para que o método nao estoure nullPointerException assim ele
     * retornará null
     * 
     * @param id
     * @return Peca
     */
    public Optional<Peca> obterPorId(Integer id) {
        return pecas.stream().filter(peca -> peca.getId() == id).findFirst();
    }

    public Peca adicionar(Peca peca) {
        ultimoId++;
        peca.setId(ultimoId);
        pecas.add(peca);
        return peca;
    }

    public void deletar(Integer id) {
        pecas.removeIf(peca -> peca.getId() == id);
    }

    /**
     * Método para atualizar peca na lista
     * 
     * @param peca que será atualizado
     * @return Retorna a peca após atualizar a lista
     */
    public Peca atualizar(Peca peca) {
        Optional<Peca> pecaEncontrado = obterPorId(peca.getId());

        if (pecaEncontrado.isPresent()) {
            throw new ResourceNotFoundException("Peça não encontrado");
        }

        deletar(peca.getId());
        pecas.add(peca);
        return peca;
    }
}
