package br.com.alura.challenge.backend.FinanceValuation.core.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;

    @Column(name = "valor")
    private BigDecimal value;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date data = new java.sql.Date(System.currentTimeMillis());

    /*TODO: tentar marcar o tempo com LocalDateTime
    private LocalDateTime data;
     */

}
