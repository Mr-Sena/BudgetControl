package br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository;

import br.com.alura.challenge.backend.FinanceValuation.core.domain.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DespesaRepository extends JpaRepository<Despesa, Long> {


}
