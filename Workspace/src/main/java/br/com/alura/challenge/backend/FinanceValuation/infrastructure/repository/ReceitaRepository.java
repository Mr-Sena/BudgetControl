package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    public List<Receita> findByData(LocalDate data);

    public List<Receita> findByDescricao(String description);

    @Query(value = "select * from receitas where month(data) = :mes and year(data) = :ano", nativeQuery = true)
    List<Receita> receitasByMonth(@Param("mes") Integer mes, @Param("ano") Integer ano);

}
