package br.com.alura.challenge.backend.FinanceValuation.service;

import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReceitaService {

    public static List<Boolean> duplicityValidation(LocalDate dataConvertida, ReceitaForm formulario, ReceitaRepository receitaRepository) {
        List<Receita> receitasByData = receitaRepository.findByData(dataConvertida);
        List<Receita> receitasByDescription = receitaRepository.findByDescricao(formulario.getDescricao());

        boolean occurrenceSameMonth = false;

        for (Receita receita : receitasByData) {

            if (receita.getData().getYear() == dataConvertida.getYear()
                    && receita.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (Receita receita : receitasByDescription) {

            if (receita.getDescricao().equals(formulario.getDescricao())) {
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
