package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Receita;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReceitaDTO {

    private String descricao;
    private String data;
    private BigDecimal valor;

    public ReceitaDTO(Receita receita) {
        this.descricao = receita.getDescricao();
        this.data = receita.getData();
        this.valor = receita.getValor();
    }

    public static List<ReceitaDTO> toReceitaDTO(List<Receita> receitas) {

        List<ReceitaDTO> receitasDTO = new ArrayList<>();

        receitas.stream().forEach(receita -> {
            ReceitaDTO receitaDTO = new ReceitaDTO(receita);
            receitasDTO.add(receitaDTO);
        });

        return receitasDTO;


        /* Abaixo, maneira enxugada de realizar a função acima
        return receitas.stream().map(ReceitaDTO::new).collect(Collectors.toList());
         */

    }

}
