package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Receita;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceitaForm {

    private String descricao;
    private String data;
    private BigDecimal valor;

    public Receita toReceita() {
        return new Receita(descricao, valor, data);
    }

}
