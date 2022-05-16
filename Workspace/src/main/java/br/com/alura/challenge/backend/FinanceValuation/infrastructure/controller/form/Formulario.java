package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form;

import br.com.alura.challenge.backend.FinanceValuation.core.Balance;

public interface Formulario {

    public Balance toDomain();

    /* Tentativa de implementação do princípio de Liskov com Interfaces (que não funcionou)
    public Balance atualizar(Long id, Repositorio repositorio);

     */

    public String getDescricao();

    public String getData();


}
