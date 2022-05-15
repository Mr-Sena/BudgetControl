package br.com.alura.challenge.backend.FinanceValuation.domain;


import br.com.alura.challenge.backend.FinanceValuation.core.Balance;
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
@Table(name = "receitas")
public class Receita implements Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String descricao;

    @NonNull
    private BigDecimal valor;

    @NonNull
    private LocalDate data;




}
