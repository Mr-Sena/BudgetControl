package br.com.alura.challenge.backend.FinanceValuation.domain;


import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
import br.com.alura.challenge.backend.FinanceValuation.domain.enuns.Categoria;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "despesas")
public class Despesa implements Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String descricao;

    @NonNull
    private BigDecimal valor;

    @NonNull
    private LocalDate data;

    private Categoria categoria;


    public Despesa(@NonNull String descricao, @NonNull BigDecimal valor, @NonNull LocalDate data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

}

