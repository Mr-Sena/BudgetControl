package br.com.alura.challenge.backend.FinanceValuation.core.domain.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String descricao;

    @NonNull
    private BigDecimal valor;

    @NonNull
    private String data;


}
