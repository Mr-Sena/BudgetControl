package br.com.alura.challenge.backend.FinanceValuation.config.security;

import br.com.alura.challenge.backend.FinanceValuation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    UsuarioRepository repository;



    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Configurações de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }


    //Configurações de Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/receitas").permitAll()
                .antMatchers(HttpMethod.GET, "/despesas").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService,
                repository), UsernamePasswordAuthenticationFilter.class); // Autenticação stateless;
    }


    //Configurações de recursos estáticos(js, css, imagens, etc.)
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /*
    //Método utilizado para fazer a geração rápida de um token
    public static void main (String args[]) {

        System.out.println(new BCryptPasswordEncoder().encode("CriptoPas5"));

        //OBS: será armazenado no banco de dados o hashing gerado para essa senha:
        //$2a$10$vu99T1GCGR2rTna65wKBHeDfNRmhnYGkrWe2CjpCodr7QcY19b2RO
        //Entretanto,  o acesso permenecerá sendo realizado com a senha CriptoPas5

    }
     */
}
