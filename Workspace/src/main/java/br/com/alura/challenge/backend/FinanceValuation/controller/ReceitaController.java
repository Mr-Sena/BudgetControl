package br.com.alura.challenge.backend.FinanceValuation.controller;


import br.com.alura.challenge.backend.FinanceValuation.config.validacao.RegistroDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.controller.DTO.ReceitaDTO;
import br.com.alura.challenge.backend.FinanceValuation.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.repository.ReceitaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
import br.com.alura.challenge.backend.FinanceValuation.service.ReceitaService;
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
@RequestMapping("/receitas")
public class ReceitaController {

    Logger logger = LoggerFactory.getLogger(ReceitaController.class);

    @Autowired
    private ReceitaRepository receitaRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<ReceitaDTO> novaReceita(@RequestBody ReceitaForm formulario, UriComponentsBuilder uriBuilder) {


        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        List<Boolean> validationResults = ReceitaService.duplicityValidation(dataConvertida, formulario, receitaRepository);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            throw new RegistroDuplicadoException();
        }


        ReceitaModel receitaModel = formulario.toReceita();
        receitaRepository.save(receitaModel);


        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(receitaModel.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceitaDTO(receitaModel));

        //return ResponseEntity.status(HttpStatus.CREATED).body(receitaRepository.save(formulario));

    }

    @GetMapping
    public List<ReceitaDTO> lista(String descricao) {

        if(descricao == null) {
            List<ReceitaModel> receitaModels = receitaRepository.findAll();
            return ReceitaDTO.toReceitasDTO(receitaModels);
        } else {
            List<ReceitaModel> receitaModels = receitaRepository.findByDescricao(descricao);
            return ReceitaDTO.toReceitasDTO(receitaModels);
        }

    }



    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> encontrarPorId (@PathVariable Long id) {


        Optional<ReceitaModel> receita = receitaRepository.findById(id);

        if(receita.isPresent()) {
            return ResponseEntity.ok(new ReceitaDTO(receita.get()));
        }

        return ResponseEntity.notFound().build();


    }


    @GetMapping("/{ano}/{mes}")
    public List<ReceitaDTO> encontrarPorAnoMes(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<ReceitaModel> receitasPorMes = receitaRepository.receitasByMonth(mes, ano);
        return ReceitaDTO.toReceitasDTO(receitasPorMes);

    }




    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReceitaDTO> resourceUpdate(@PathVariable Long id, @RequestBody ReceitaForm formulario) {

        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        Optional<ReceitaModel> thisReceita = receitaRepository.findById(id);
        if(thisReceita.isPresent()) {

            List<Boolean> validationResults = ReceitaService.duplicityValidation(dataConvertida, formulario, receitaRepository);

            boolean occurrenceSameMonth = validationResults.get(0);
            boolean occurrenceSameDescription = validationResults.get(1);

            if (occurrenceSameDescription && occurrenceSameMonth) {
                throw new RegistroDuplicadoException();
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
