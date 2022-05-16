package br.com.alura.challenge.backend.FinanceValuation.service;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.core.Repositorio;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.validacao.RegistroDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.DespesaForm;
import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.Formulario;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ValidationService {


    public static List<Boolean> duplicityValidation(LocalDate dataConvertida, Formulario formulario,
                                                    Repositorio repositorio) {
        List<Balance> despesasByData = repositorio.findByData(dataConvertida);
        List<Balance> despesasByDescription = repositorio.findByDescricao(formulario.getDescricao());

        boolean occurrenceSameMonth = false;

        for (Balance saldo : despesasByData) {

            if (saldo.getData().getYear() == dataConvertida.getYear()
                    && saldo.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (Balance despesa : despesasByDescription) {

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

    public static void validarDuplicidade(Formulario formulario, Repositorio repository) {

        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        List<Boolean> validationResults = ValidationService.duplicityValidation(dataConvertida, formulario, repository);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            throw new RegistroDuplicadoException();
        }


    }


}
