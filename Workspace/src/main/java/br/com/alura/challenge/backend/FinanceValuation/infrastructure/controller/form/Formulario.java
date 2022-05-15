package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.core.Repositorio;
import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.DespesaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Formulario {

    public Balance toDomain();

    /* Tentativa de implementação do princípio de Liskov com Interfaces (que não funcionou)
    public Balance atualizar(Long id, Repositorio repositorio);

     */

    public String getDescricao();


}
