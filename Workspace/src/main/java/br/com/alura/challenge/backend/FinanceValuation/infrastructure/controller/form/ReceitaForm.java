package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class ReceitaForm implements Formulario{

    private Long id;
    private String descricao;
    private String data;
    private BigDecimal valor;

    @Override
    public Receita toDomain() {

        LocalDate dataConvertida = DataConverter.toConvert(data);
        return new Receita(descricao, valor, dataConvertida);
    }


    public Receita atualizar(Long id, ReceitaRepository repository) {

        Optional<Receita> receita = repository.findById(id);
        receita.get().setValor(this.valor);
        receita.get().setDescricao(this.descricao);
        LocalDate dataConvertida = DataConverter.toConvert(data);
        receita.get().setData(dataConvertida);

        return receita.get();


    }


}
