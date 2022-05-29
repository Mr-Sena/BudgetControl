package br.com.alura.challenge.backend.FinanceValuation.service;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.core.Repositorio;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.validacao.RegistroDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.Formulario;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ValidationService {


    public static List<Boolean> validate(LocalDate dataConvertida, Formulario formulario,
                                         Repositorio repositorio) {

        List<Balance> registrosByData = repositorio.findByData(dataConvertida);
        List<Balance> registrosByDescription = repositorio.findByDescricao(formulario.getDescricao());

        boolean occurrenceSameMonth = false;

        for (Balance saldo : registrosByData) {

            if (saldo.getData().getYear() == dataConvertida.getYear()
                    && saldo.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (Balance saldo : registrosByDescription) {

            if (saldo.getDescricao().equals(formulario.getDescricao())) {
                occurrenceSameDescription = true;
                break;
            } else
                occurrenceSameDescription = false;

        }

        List<Boolean> validacoes = Arrays.asList(occurrenceSameMonth, occurrenceSameDescription);

        return validacoes;

    }

    public static void validarDuplicidade(Formulario formulario, Repositorio repository) {

        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        List<Boolean> validationResults = ValidationService.validate(dataConvertida, formulario, repository);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            throw new RegistroDuplicadoException();
        }


    }







    public static void validarDuplicidade(Formulario formulario, Repositorio repository, Long idToBeUpdated) {


        LocalDate dataConvertida = DataConverter.toConvert(formulario.getData());

        List<Boolean> validationResults = cleanUpdate(dataConvertida, formulario, repository, idToBeUpdated);

        boolean occurrenceSameMonth = validationResults.get(0);
        boolean occurrenceSameDescription = validationResults.get(1);

        if (occurrenceSameDescription && occurrenceSameMonth) {
            throw new RegistroDuplicadoException();
        }


    }

    public static List<Boolean> cleanUpdate(LocalDate dataConvertida, Formulario formulario,
                                            Repositorio repositorio, Long idToBeUpdated) {

        List<Balance> registrosByData = repositorio.findByData(dataConvertida);
        List<Balance> registrosByDescription = repositorio.findByDescricao(formulario.getDescricao());


        Balance toRemove = null;

        for (Balance registro : registrosByData) {
            if (registro.getId() == idToBeUpdated) {
                toRemove = registro;
            }
        }
        registrosByData.remove(toRemove);


        for (Balance registro : registrosByDescription) {
            if (registro.getId() == idToBeUpdated) {
                toRemove = registro;
            }
        }
        registrosByDescription.remove(toRemove);

        return validateToUpdate(registrosByData, registrosByDescription, dataConvertida, formulario);


    }

    public static List<Boolean> validateToUpdate(List<Balance> registroByData,
                                                 List<Balance> registroByDescription, LocalDate dataConvertida, Formulario formulario) {

        boolean occurrenceSameMonth = false;

        for (Balance registros : registroByData) {

            if (registros.getData().getYear() == dataConvertida.getYear()
                    && registros.getData().getMonth() == dataConvertida.getMonth()) {
                occurrenceSameMonth = true;
                break;
            } else
                occurrenceSameMonth = false;

        }


        boolean occurrenceSameDescription = false;
        for (Balance registros : registroByDescription) {

            if (registros.getDescricao().equals(formulario.getDescricao())) {
                occurrenceSameDescription = true;
                break;
            } else
                occurrenceSameDescription = false;

        }

        List<Boolean> validacoes = Arrays.asList(occurrenceSameMonth, occurrenceSameDescription);
        return validacoes;

    }





}
