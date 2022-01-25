package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.enuns.Categoria;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@Setter
public class DespesaForm {

    private String descricao;
    private String data;
    private BigDecimal valor;
    private String categoria;

    public Despesa toDespesa() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(data, formatter);

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        despesa.get().setData(LocalDate.parse(this.data, formatter));

        return despesa.get();


    }


}
