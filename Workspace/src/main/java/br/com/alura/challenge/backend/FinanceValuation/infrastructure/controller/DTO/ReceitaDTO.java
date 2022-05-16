package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.DTO;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReceitaDTO {

    private Long id;
    private String descricao;
    private LocalDate data;
    private BigDecimal valor;

    public ReceitaDTO(Receita receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.data = receita.getData();
        this.valor = receita.getValor();
    }

    public static List<ReceitaDTO> toReceitasDTO(List<Balance> receitas) {

        List<ReceitaDTO> receitasDTO = new ArrayList<>();

        receitas.stream().forEach(receitaModel -> {
            ReceitaDTO receitaDTO = new ReceitaDTO((Receita) receitaModel);
            receitasDTO.add(receitaDTO);
        });

        return receitasDTO;


        /* Abaixo, maneira enxugada de realizar a função acima
        return receitas.stream().map(ReceitaDTO::new).collect(Collectors.toList());
         */

    }



}
