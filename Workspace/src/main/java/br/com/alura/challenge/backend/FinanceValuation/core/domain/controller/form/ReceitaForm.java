package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ReceitaForm {

    private String descricao;
    private String data;
    private BigDecimal valor;

    public ReceitaModel toReceita() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(data, formatter);
        return new ReceitaModel(descricao, valor, dataConvertida);
    }

}
