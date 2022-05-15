package br.com.alura.challenge.backend.FinanceValuation.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResumoMes {

    private BigDecimal valorTotalReceitas;
    private BigDecimal valorTotalDespesas;
    private BigDecimal valorFinal;
    private List<String> custoByCategorias;





}
