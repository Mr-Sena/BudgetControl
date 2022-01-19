package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaRepository despesaRepository;

    @PostMapping
    @Transactional
    ResponseEntity<Despesa> novaDespesa(@RequestBody Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaRepository.save(despesa));
    }

    @GetMapping
    public List<Despesa> buscarTodos() {
        return despesaRepository.findAll();
    }

}
