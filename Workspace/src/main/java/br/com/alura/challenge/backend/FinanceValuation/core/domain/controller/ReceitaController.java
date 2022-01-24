package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;


import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO.ReceitaDTO;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.service.ReceitaService;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import jdk.javadoc.doclet.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static br.com.alura.challenge.backend.FinanceValuation.core.domain.service.ReceitaService.businessRuleValidation;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    Logger logger = LoggerFactory.getLogger(ReceitaController.class);

    @Autowired
    private ReceitaRepository receitaRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<ReceitaDTO> novaReceita(@RequestBody ReceitaForm formulario, UriComponentsBuilder uriBuilder) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(formulario.getData(), formatter);

        List<Boolean> validationResults = businessRuleValidation(dataConvertida, formulario, receitaRepository);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            return ResponseEntity.badRequest().build();
        }


        ReceitaModel receitaModel = formulario.toReceita();
        receitaRepository.save(receitaModel);


        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(receitaModel.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceitaDTO(receitaModel));

        //return ResponseEntity.status(HttpStatus.CREATED).body(receitaRepository.save(formulario));

    }

    @GetMapping
    public List<ReceitaDTO> buscarTodos() {

        List<ReceitaModel> receitaModels = receitaRepository.findAll();
        return ReceitaDTO.toReceitaDTO(receitaModels);

    }



    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> encontrarPorId (@PathVariable Long id) {


        Optional<ReceitaModel> receita = receitaRepository.findById(id);

        if(receita.isPresent()) {
            return ResponseEntity.ok(new ReceitaDTO(receita.get()));
        }

        return ResponseEntity.notFound().build();


    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReceitaDTO> resourceUpdate(@PathVariable Long id, @RequestBody ReceitaForm formulario) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(formulario.getData(), formatter);

        Optional<ReceitaModel> thisReceita = receitaRepository.findById(id);
        if(thisReceita.isPresent()) {

            List<Boolean> validationResults = businessRuleValidation(dataConvertida, formulario, receitaRepository);

            boolean occurrenceSameMonth = validationResults.get(0);
            boolean occurrenceSameDescription = validationResults.get(1);

            if (occurrenceSameDescription && occurrenceSameMonth) {
                return ResponseEntity.badRequest().build();
            }

            ReceitaModel receita = formulario.atualizar(id, receitaRepository);
            return ResponseEntity.ok(new ReceitaDTO(receita));
        }

        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {

        Optional<ReceitaModel> thisReceita = receitaRepository.findById(id);

        if(thisReceita.isPresent()) {

            receitaRepository.deleteById(id);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();

    }


}
