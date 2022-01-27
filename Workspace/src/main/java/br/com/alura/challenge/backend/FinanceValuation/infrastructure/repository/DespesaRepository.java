package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    public List<Despesa> findByData(LocalDate data);

    public List<Despesa> findByDescricao(String description);

    @Query(value = "select * from despesas where month = :month and year = :year", nativeQuery = true)
    public List<Despesa> despesasByMonth(@Param("month") Integer mes, @Param("year") Integer ano);


}
