package br.com.alura.challenge.backend.FinanceValuation.service;

import br.com.alura.challenge.backend.FinanceValuation.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.repository.ReceitaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReceitaService {

    public static List<Boolean> duplicityValidation(LocalDate dataConvertida, ReceitaForm formulario, ReceitaRepository receitaRepository) {
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

        List<Boolean> validacoes = Arrays.asList(occurrenceSameMonth, occurrenceSameDescription);

        /*
        logger.info("Ocorrência com a mesma descrição(?): " + occurrenceSameDescription);

        logger.info("Existe ocorrência nesse mês(?): " + occurrenceSameMonth);
         */

        return validacoes;

    }

}
