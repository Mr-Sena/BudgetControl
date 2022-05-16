package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.domain.enuns.Categoria;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.DataConverter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class DespesaForm implements Formulario{

    private Long id;
    private String descricao;
    private String data;
    private BigDecimal valor;
    private String categoria;

    @Override
    public Despesa toDomain() {
        LocalDate dataConvertida = DataConverter.toConvert(data);

        if (categoria != null) {
            Categoria categoriaByString = Categoria.valueOf( categoria.toUpperCase() );
            return new Despesa(descricao, valor, dataConvertida, categoriaByString);
        }

        return new Despesa(descricao, valor, dataConvertida);

    }


    public Despesa atualizar(Long id, DespesaRepository repository) {

        Optional<Despesa> despesa = repository.findById(id);
        despesa.get().setValor(this.valor);
        despesa.get().setDescricao(this.descricao);
        LocalDate dataConvertida = DataConverter.toConvert(data);
        despesa.get().setData(dataConvertida);

        return despesa.get();


    }


}
