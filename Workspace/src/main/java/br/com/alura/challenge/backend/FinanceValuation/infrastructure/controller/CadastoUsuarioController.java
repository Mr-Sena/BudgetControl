package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller;

import br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.validacao.UsuarioDuplicadoException;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.DTO.UsuarioDTO;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form.CadastroForm;
import br.com.alura.challenge.backend.FinanceValuation.domain.Usuario;
import br.com.alura.challenge.backend.FinanceValuation.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cadastro")
public class CadastoUsuarioController {


    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;
    //Obs: O PasswordEncoder foi configurado na classe main do projeto [App] com as definições de um BCrypt


    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody CadastroForm form) {

        Usuario usuario = new Usuario();
        usuario.setEmail(form.getEmail());
        usuario.setNome(form.getNome());
        usuario.setSenha(encoder.encode(form.getSenha()));

        try {
            repository.save(usuario);
        } catch (DataIntegrityViolationException exception) {
            throw new UsuarioDuplicadoException();
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getNome(), usuario.getEmail(), usuario.getSenha());


        return ResponseEntity.ok(usuarioDTO);

    }


}
