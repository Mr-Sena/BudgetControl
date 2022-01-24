package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DespesaDTO {

    private String descricao;
    private LocalDate data;
    private BigDecimal valor;

    public DespesaDTO(Despesa despesa) {
        this.descricao = despesa.getDescricao();
        this.data = despesa.getData();
        this.valor = despesa.getValor();
    }

    public static List<DespesaDTO> toDespesaDTO(List<Despesa> despesas) {

        List<DespesaDTO> despesasDTO = new ArrayList<>();

        despesas.stream().forEach(despesa -> {
            DespesaDTO despesaDTO = new DespesaDTO(despesa);
            despesasDTO.add(despesaDTO);
        });

        return despesasDTO;

    }

}
