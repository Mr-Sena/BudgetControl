package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.ReceitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
public interface ReceitaRepository extends JpaRepository<ReceitaModel, Long> {

    public List<ReceitaModel> findByData(LocalDate data);

    public List<ReceitaModel> findByDescricao(String description);

}
