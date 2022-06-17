package br.com.alura.challenge.backend.FinanceValuation.core;

import br.com.alura.challenge.backend.FinanceValuation.domain.enuns.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Balance {

    public LocalDate getData();

    public String getDescricao();

    public Long getId();


    BigDecimal getValor();

}
