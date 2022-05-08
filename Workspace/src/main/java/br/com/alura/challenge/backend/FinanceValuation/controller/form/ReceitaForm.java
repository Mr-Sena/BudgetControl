package br.com.alura.challenge.backend.FinanceValuation.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.repository.ReceitaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class ReceitaForm {

    private Integer id;
    private String descricao;
    private String data;
    private BigDecimal valor;

    public ReceitaModel toReceita() {

        LocalDate dataConvertida = DataConverter.toConvert(data);
        return new ReceitaModel(descricao, valor, dataConvertida);
    }


    public ReceitaModel atualizar(Long id, ReceitaRepository repository) {

        Optional<ReceitaModel> receita = repository.findById(id);
        receita.get().setValor(this.valor);
        receita.get().setDescricao(this.descricao);
        LocalDate dataConvertida = DataConverter.toConvert(data);
        receita.get().setData(dataConvertida);

        return receita.get();


    }


}
