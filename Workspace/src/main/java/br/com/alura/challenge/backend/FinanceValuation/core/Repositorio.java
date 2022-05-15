package br.com.alura.challenge.backend.FinanceValuation.core;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface Repositorio {

    public List<Despesa> findByData(LocalDate data);

    public List<Despesa> findByDescricao(String description);

    @Query(value = "select * from despesas where month(data) = :month and year(data) = :year", nativeQuery = true)
    public List<Despesa> despesasByMonth(@Param("month") Integer mes, @Param("year") Integer ano);
}
