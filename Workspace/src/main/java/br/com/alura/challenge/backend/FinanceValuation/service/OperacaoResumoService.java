package br.com.alura.challenge.backend.FinanceValuation.service;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import br.com.alura.challenge.backend.FinanceValuation.domain.ResumoMes;
import br.com.alura.challenge.backend.FinanceValuation.domain.enuns.Categoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperacaoResumoService {




    public static ResumoMes calcular(List<Receita> receitasPorMes, List<Despesa> despesasPorMes) {

    Logger logger = LoggerFactory.getLogger(OperacaoResumoService.class);

        BigDecimal valorTotalReceitas = new BigDecimal("0");

        for (Receita receita : receitasPorMes) {
            logger.info("\nvalor unitário dessa receita (" +receita.getDescricao() +  "): " + receita.getValor());
            valorTotalReceitas = valorTotalReceitas.add(receita.getValor());
            logger.info("\nvalor total das receitas: " + valorTotalReceitas);
        }


        BigDecimal valorTotalDespesas = new BigDecimal("0");

        for (Despesa despesa : despesasPorMes) {
            logger.info("\n\n\nvalor unitário dessa despesa (" +despesa.getDescricao() +  "): " + despesa.getValor());
            valorTotalDespesas = valorTotalDespesas.add(despesa.getValor());
            logger.info("\nvalor total das despesas: " + valorTotalDespesas);
        }


        ResumoMes resumoMes = new ResumoMes();
        resumoMes.setValorTotalReceitas(valorTotalReceitas);
        resumoMes.setValorTotalDespesas(valorTotalDespesas);
        resumoMes.setValorFinal(valorTotalReceitas.subtract(valorTotalDespesas));



        List categorias = Arrays.asList(
                "Custo em ALIMENTACAO",
                "Custo em SAUDE",
                "Custo em MORADIA",
                "Custo em TRANSPORTE",
                "Custo em EDUCACAO",
                "Custo em LAZER",
                "Custo em IMPREVISTOS",
                "Custo em OUTRAS"
        );

        List custoByCategorias = new ArrayList();

        int index = 0;
        for (Categoria categoria : Categoria.values()) {

            BigDecimal valorPorCategoria = new BigDecimal("0");

            for (Despesa despesa : despesasPorMes) {
                if (categoria.equals(despesa.getCategoria())) {
                    valorPorCategoria = valorPorCategoria.add(despesa.getValor());
                }
            }


            custoByCategorias.add(categorias.get(index) + ": R$" + valorPorCategoria);

            index++;
        }

        resumoMes.setCustoByCategorias(custoByCategorias);

        return resumoMes;

    }

}
