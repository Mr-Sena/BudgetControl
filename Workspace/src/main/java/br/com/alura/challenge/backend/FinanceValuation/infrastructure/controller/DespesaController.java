package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller;

import br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.validacao.RegistroDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.DTO.DespesaDTO;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.DespesaForm;
import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
import br.com.alura.challenge.backend.FinanceValuation.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/despesas")
public class DespesaController {


    Logger logger = LoggerFactory.getLogger(DespesaController.class);

    @Autowired
    private DespesaRepository despesaRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaForm formulario, UriComponentsBuilder uriBuilder) {


        ValidationService.validarDuplicidade(formulario, despesaRepository);

        try {
            Despesa despesa = formulario.toDomain();
            despesaRepository.save(despesa);

            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(despesa.getId()).toUri();
            return ResponseEntity.created(uri).body(new DespesaDTO(despesa));

        } catch (IllegalArgumentException exception) {
            logger.error("\nNão existe essa categoria: " + formulario.getCategoria() +
                    "\nRedireconando para o tipo \"OUTRAS\"...");

            formulario.setCategoria("OUTRAS");
            Despesa despesa = formulario.toDomain();
            despesaRepository.save(despesa);
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(despesa.getId()).toUri();
            return ResponseEntity.created(uri).body(new DespesaDTO(despesa));
        }
    }

    @GetMapping
    public List<DespesaDTO> lista(String descricao) {

        if(descricao == null) {
            List<Despesa> despesas = despesaRepository.findAll();
            return DespesaDTO.toDespesasDTO(despesas);
        } else {
            List despesas = despesaRepository.findByDescricao(descricao);
            return DespesaDTO.toDespesasDTO(despesas);
        }


    }



    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> encontrarPorId (@PathVariable Long id) {


        Optional<Despesa> despesa = despesaRepository.findById(id);

        if(despesa.isPresent()) {
            return ResponseEntity.ok(new DespesaDTO(despesa.get()));
        }

        return ResponseEntity.notFound().build();


    }

    @GetMapping("/{ano}/{mes}")
    public List<DespesaDTO> encontrarPorAnoMes(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<Despesa> despesasNoMes = despesaRepository.despesasByMonth(mes, ano);
        return DespesaDTO.toDespesasDTO(despesasNoMes);

    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DespesaDTO> resourceUpdate(@PathVariable Long id, @RequestBody DespesaForm formulario) {


        Optional<Despesa> thisDespesa = despesaRepository.findById(id);

        if(thisDespesa.isPresent()) {

            ValidationService.validarDuplicidade(formulario, despesaRepository, id);

            Despesa despesa = formulario.atualizar(id, despesaRepository);
            return ResponseEntity.ok(new DespesaDTO(despesa));
        }


        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {

        Optional<Despesa> thisDespesa = despesaRepository.findById(id);

        if(thisDespesa.isPresent()) {

            despesaRepository.deleteById(id);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();

    }


}
