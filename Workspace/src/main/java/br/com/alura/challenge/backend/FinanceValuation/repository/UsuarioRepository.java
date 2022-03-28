package br.com.alura.challenge.backend.FinanceValuation.repository;

import br.com.alura.challenge.backend.FinanceValuation.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
