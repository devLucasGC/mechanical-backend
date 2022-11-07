package com.mechanical.mechanicalsite.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mechanical.mechanicalsite.service.PecaService;
import com.mechanical.mechanicalsite.shared.PecaDTO;
import com.mechanical.mechanicalsite.view.model.PecaRequest;
import com.mechanical.mechanicalsite.view.model.PecaResponse;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @GetMapping
    public ResponseEntity<List<PecaResponse>> obterTodos() {
        List<PecaDTO> pecas = pecaService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<PecaResponse> resposta = pecas.stream()
                .map(peca -> mapper.map(peca, PecaResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PecaResponse>> obterPorId(@PathVariable Integer id) {
        Optional<PecaDTO> dto = pecaService.obterPorId(id);
        PecaResponse peca = new ModelMapper().map(dto.get(), PecaResponse.class);
        return new ResponseEntity<>(Optional.of(peca), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PecaResponse> adicionar(@RequestBody PecaRequest pecaReq) {
        ModelMapper mapper = new ModelMapper();
        PecaDTO pecaDto = mapper.map(pecaReq, PecaDTO.class);
        pecaDto = pecaService.adicionar(pecaDto);
        return new ResponseEntity<>(mapper.map(pecaDto, PecaResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        pecaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaResponse> atualizar(@RequestBody PecaRequest pecaReq, @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        PecaDTO pecaDto = mapper.map(pecaReq, PecaDTO.class);
        pecaDto = pecaService.atualizar(id, pecaDto);
        return new ResponseEntity<>(
                mapper.map(pecaDto, PecaResponse.class),
                HttpStatus.OK);
    }
}
