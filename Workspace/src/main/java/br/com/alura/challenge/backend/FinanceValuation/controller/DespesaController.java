package br.com.alura.challenge.backend.FinanceValuation.controller;

import br.com.alura.challenge.backend.FinanceValuation.config.validacao.RegistroDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.controller.DTO.DespesaDTO;
import br.com.alura.challenge.backend.FinanceValuation.controller.form.DespesaForm;
import br.com.alura.challenge.backend.FinanceValuation.model.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
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

import static br.com.alura.challenge.backend.FinanceValuation.service.DespesaService.duplicityValidation;


@RestController
@RequestMapping("/despesas")
public class DespesaController {


    Logger logger = LoggerFactory.getLogger(DespesaController.class);

    @Autowired
    private DespesaRepository despesaRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<DespesaDTO> novaDespesa(@RequestBody DespesaForm formulario, UriComponentsBuilder uriBuilder) {


        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        List<Boolean> validationResults = duplicityValidation(dataConvertida, formulario, despesaRepository);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            throw new RegistroDuplicadoException();
        }


        try {

            Despesa despesa = formulario.toDespesa();
            despesaRepository.save(despesa);

            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(despesa.getId()).toUri();
            return ResponseEntity.created(uri).body(new DespesaDTO(despesa));

        } catch (IllegalArgumentException exception) {
            logger.error("\nNão existe essa categoria: " + formulario.getCategoria() +
                    "\nRedireconando para o tipo \"OUTRAS\"...");

            formulario.setCategoria("OUTRAS");
            Despesa despesa = formulario.toDespesa();
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
            List<Despesa> despesas = despesaRepository.findByDescricao(descricao);
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

        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        Optional<Despesa> thisDespesa = despesaRepository.findById(id);
        if(thisDespesa.isPresent()) {

            List<Boolean> validationResults = duplicityValidation(dataConvertida, formulario, despesaRepository);

            boolean occurrenceSameMonth = validationResults.get(0);
            boolean occurrenceSameDescription = validationResults.get(1);

            if (occurrenceSameDescription && occurrenceSameMonth) {
                return ResponseEntity.badRequest().build();
            }

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
