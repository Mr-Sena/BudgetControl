package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import br.com.alura.challenge.backend.FinanceValuation.domain.ResumoMes;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.ReceitaRepository;
import br.com.alura.challenge.backend.FinanceValuation.service.OperacaoResumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.alura.challenge.backend.FinanceValuation.service.OperacaoResumoService.calcular;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @GetMapping("/{ano}/{mes}")
    public ResumoMes contaFinalDoMes(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<Receita> receitasPorMes = receitaRepository.receitasByMonth(mes, ano);
        List<Despesa> despesasPorMes = despesaRepository.despesasByMonth(mes, ano);

        return calcular(receitasPorMes, despesasPorMes);

    }

}
