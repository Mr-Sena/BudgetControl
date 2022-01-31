package br.com.alura.challenge.backend.FinanceValuation.core.domain.controller;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.enuns.Categoria;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ResumoMes;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    Logger logger = LoggerFactory.getLogger(ResumoController.class);


    @GetMapping("/{ano}/{mes}")
    public ResumoMes contaFinalDoMes(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<ReceitaModel> receitasPorMes = receitaRepository.receitasByMonth(mes, ano);
        List<Despesa> despesasPorMes = despesaRepository.despesasByMonth(mes, ano);

        BigDecimal valorTotalReceitas = new BigDecimal("0");

        for (ReceitaModel receita : receitasPorMes) {
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



        //List custoByCategorias = new ArrayList();
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
