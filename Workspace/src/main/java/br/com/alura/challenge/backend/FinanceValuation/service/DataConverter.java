package br.com.alura.challenge.backend.FinanceValuation.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataConverter {

    public static LocalDate toConvert(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(date, formatter);

        return dataConvertida;

    }

}
