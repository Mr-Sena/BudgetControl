package br.com.alura.challenge.backend.FinanceValuation.core.domain.model;


import br.com.alura.challenge.backend.FinanceValuation.core.domain.controller.DTO.ReceitaDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "receitas")
public class ReceitaModel {

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
