package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReceitaDTO {

    private String descricao;
    private LocalDate data;
    private BigDecimal valor;

    public ReceitaDTO(ReceitaModel receitaModel) {
        this.descricao = receitaModel.getDescricao();
        this.data = receitaModel.getData();
        this.valor = receitaModel.getValor();
    }

    public static List<ReceitaDTO> toReceitaDTO(List<ReceitaModel> receitaModels) {

        List<ReceitaDTO> receitasDTO = new ArrayList<>();

        receitaModels.stream().forEach(receitaModel -> {
            ReceitaDTO receitaDTO = new ReceitaDTO(receitaModel);
            receitasDTO.add(receitaDTO);
        });

        return receitasDTO;


        /* Abaixo, maneira enxugada de realizar a função acima
        return receitas.stream().map(ReceitaDTO::new).collect(Collectors.toList());
         */

    }



}
