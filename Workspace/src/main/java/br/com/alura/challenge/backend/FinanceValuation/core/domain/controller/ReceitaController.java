package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;


import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO.ReceitaDTO;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Receita;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {


    //Tentar depois sem Autowired (reconhecimento automático na semelhança dos nomes)
    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ReceitaDTO> novaReceita(@RequestBody ReceitaForm formulario, UriComponentsBuilder uriBuilder) {

        Receita receita = formulario.toReceita();
        receitaRepository.save(receita);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(receita.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceitaDTO(receita));

        //return ResponseEntity.status(HttpStatus.CREATED).body(receitaRepository.save(formulario));

    }

    @GetMapping
    public List<ReceitaDTO> buscarTodos() {

        List<Receita> receitas = receitaRepository.findAll();
        return ReceitaDTO.toReceitaDTO(receitas);

    }



}
