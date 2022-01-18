package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;


import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Receita;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/receita")
public class ReceitaController {


    //Tentar depois sem Autowired (reconhecimento automático na semelhança dos nomes)
    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Receita> novaReceita(@RequestBody Receita modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(receitaRepository.save(modelo));
    }

    @GetMapping
    public List<Receita> buscarTodos() {
        return receitaRepository.findAll();
    }



}
