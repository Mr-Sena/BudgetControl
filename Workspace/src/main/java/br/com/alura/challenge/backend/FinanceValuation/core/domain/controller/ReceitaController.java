package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;


import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO.ReceitaDTO;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

        List<ReceitaModel> receitasByData = receitaRepository.findByData(dataConvertida);
        List<ReceitaModel> receitasByDescription = receitaRepository.findByDescricao(formulario.getDescricao());

        boolean occurrenceSameMonth = false;

        for (ReceitaModel receitaModel : receitasByData) {

            if (receitaModel.getData().getYear() == dataConvertida.getYear()
                    && receitaModel.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (ReceitaModel receitaModel : receitasByDescription) {

            if (receitaModel.getDescricao().equals(formulario.getDescricao())) {
                occurrenceSameDescription = true;
                break;
            } else
                occurrenceSameDescription = false;

        }


        /*
        logger.info("Ocorrência com a mesma descrição(?): " + occurrenceSameDescription);

        logger.info("Existe ocorrência nesse mês(?): " + occurrenceSameMonth);
         */

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


    //return ResponseEntity.ok(receitaRepository.findByData(LocalDate.now()));


}
