package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.core.Repositorio;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
public interface ReceitaRepository extends Repositorio, JpaRepository<Receita, Long> {

    public List<Balance> findByData(LocalDate data);

    public List<Balance> findByDescricao(String description);

    @Query(value = "select * from receitas where month(data) = :mes and year(data) = :ano", nativeQuery = true)
    List<Receita> receitasByMonth(@Param("mes") Integer mes, @Param("ano") Integer ano);

}
