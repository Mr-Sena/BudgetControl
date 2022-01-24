package br.com.alura.challenge.backend.FinanceValuation.core.domain.service;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form.DespesaForm;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DespesaService {

    public static List<Boolean> businessRuleValidation(LocalDate dataConvertida, DespesaForm formulario, DespesaRepository despesaRepository) {
        List<Despesa> despesasByData = despesaRepository.findByData(dataConvertida);
        List<Despesa> despesasByDescription = despesaRepository.findByDescricao(formulario.getDescricao());

        boolean occurrenceSameMonth = false;

        for (Despesa despesa : despesasByData) {

            if (despesa.getData().getYear() == dataConvertida.getYear()
                    && despesa.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (Despesa despesa : despesasByDescription) {

            if (despesa.getDescricao().equals(formulario.getDescricao())) {
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
