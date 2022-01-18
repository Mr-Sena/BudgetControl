package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;


@RestController
public interface ReceitaRepository extends JpaRepository<Receita, Long> {


}
