package br.com.alura.challenge.backend.FinanceValuation.core;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface Repositorio {

    public List<Balance> findByData(LocalDate data);

    public List<Balance> findByDescricao(String description);

}
