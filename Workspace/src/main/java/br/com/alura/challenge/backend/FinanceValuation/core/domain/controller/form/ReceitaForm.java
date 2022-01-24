package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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


    public ReceitaModel atualizar(Long id, ReceitaRepository repository) {

        Optional<ReceitaModel> receita = repository.findById(id);
        receita.get().setValor(this.valor);
        receita.get().setDescricao(this.descricao);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        receita.get().setData(LocalDate.parse(this.data, formatter));

        return receita.get();


    }


}
