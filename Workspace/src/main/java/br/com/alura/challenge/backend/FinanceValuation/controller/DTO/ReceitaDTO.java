package br.com.alura.challenge.backend.FinanceValuation.controller.DTO;

import br.com.alura.challenge.backend.FinanceValuation.model.ReceitaModel;
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

    public ReceitaDTO(ReceitaModel receitaModel) {
        this.id = receitaModel.getId();
        this.descricao = receitaModel.getDescricao();
        this.data = receitaModel.getData();
        this.valor = receitaModel.getValor();
    }

    public static List<ReceitaDTO> toReceitasDTO(List<ReceitaModel> receitaModels) {

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
