package com.mechanical.mechanicalsite.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mechanical.mechanicalsite.model.Peca;
import com.mechanical.mechanicalsite.model.exception.ResourceNotFoundException;
import com.mechanical.mechanicalsite.repository.PecaRepository;
import com.mechanical.mechanicalsite.shared.PecaDTO;

@Service
public class PecaService {
    @Autowired // serve para instanciar automaticamente
    private PecaRepository pecaRepository;

    public List<PecaDTO> obterTodos() {
        // Retorna uma lista de peca model
        List<Peca> pecas = pecaRepository.findAll();

        return pecas.stream()
                .map(peca -> new ModelMapper().map(peca, PecaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Optional serve para que o método nao estoure nullPointerException assim ele
     * retornará null
     * 
     * @param id
     * @return Peca
     */
    public Optional<PecaDTO> obterPorId(Integer id) {
        // obtendo Optional de peca pelo id
        Optional<Peca> peca = pecaRepository.findById(id);

        if (!peca.isPresent()) {
            throw new ResourceNotFoundException(
                    "Peça com o id " + id + " - Não encontrado.");
        }

        // cpnvertendo meu optional de peca pra pecadto
        PecaDTO dto = new ModelMapper().map(peca.get(), PecaDTO.class);
        // criando e retornando um optional de pecadto
        return Optional.of(dto);
    }

    public PecaDTO adicionar(PecaDTO peca) {
        peca.setId(null);

        // cria um obj de mapeamento
        ModelMapper mapper = new ModelMapper();

        // converter pecadto em um peca
        Peca pec = mapper.map(peca, Peca.class);

        // salver o peca no banco
        pec = pecaRepository.save(pec);
        peca.setId(pec.getId());

        // retornar o pecadto atualizado
        return peca;
    }

    public void deletar(Integer id) {
        // verifica se peca existe
        Optional<Peca> peca = pecaRepository.findById(id);

        // se não existir lança uma exception
        if (!peca.isPresent()) {
            throw new ResourceNotFoundException(
                    "Não foi possivel deletar a peça com o id" + id + " - Peça não existe.");
        }

        // deleta peca pelo id
        pecaRepository.deleteById(id);
    }

    public PecaDTO atualizar(Integer id, PecaDTO pecaDto) {
        // passar o id para o pecaDto
        pecaDto.setId(id);

        // criar um obj de mapeamento
        ModelMapper mapper = new ModelMapper();

        // conveter o pecadto em um peca
        Peca peca = mapper.map(pecaDto, Peca.class);

        // atualizar o peca no banco
        pecaRepository.save(peca);

        // retornar o pecaDto atualizado
        return pecaDto;
    }
}
