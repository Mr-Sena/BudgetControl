package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller;


import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.DTO.ReceitaDTO;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    Logger logger = LoggerFactory.getLogger(ReceitaController.class);

    @Autowired
    private ReceitaRepository receitaRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<ReceitaDTO> novaReceita(@RequestBody ReceitaForm formulario, UriComponentsBuilder uriBuilder) {

        ValidationService.validarDuplicidade(formulario, receitaRepository);
        Receita receita = formulario.toDomain();
        receitaRepository.save(receita);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(receita.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceitaDTO(receita));

    }

    @GetMapping
    public List<ReceitaDTO> lista(String descricao) {

        if(descricao == null) {
            List receitas = receitaRepository.findAll();
            return ReceitaDTO.toReceitasDTO(receitas);
        } else {
            List<Balance> receitas = receitaRepository.findByDescricao(descricao);
            return ReceitaDTO.toReceitasDTO(receitas);
        }

    }



    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> encontrarPorId (@PathVariable Long id) {


        Optional<Receita> receita = receitaRepository.findById(id);

        if(receita.isPresent()) {
            return ResponseEntity.ok(new ReceitaDTO(receita.get()));
        }

        return ResponseEntity.notFound().build();


    }


    @GetMapping("/{ano}/{mes}")
    public List<ReceitaDTO> encontrarPorAnoMes(@PathVariable Integer ano, @PathVariable Integer mes) {

        List receitasPorMes = receitaRepository.receitasByMonth(mes, ano);
        return ReceitaDTO.toReceitasDTO(receitasPorMes);

    }




    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReceitaDTO> resourceUpdate(@PathVariable Long id, @RequestBody ReceitaForm formulario) {


        Optional<Receita> thisReceita = receitaRepository.findById(id);

        if(thisReceita.isPresent()) {

            ValidationService.validarDuplicidade(formulario, receitaRepository, id);

            Receita receita = formulario.atualizar(id, receitaRepository);
            return ResponseEntity.ok(new ReceitaDTO(receita));
        }

        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {

        Optional<Receita> thisReceita = receitaRepository.findById(id);

        if(thisReceita.isPresent()) {

            receitaRepository.deleteById(id);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();

    }


}
