package curso.api.rest.security;

import curso.api.rest.service.ImplementacaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/*Mapear URL, endereços, autoriza ou bloqueia acessos a url´s.*/

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    //Configura as solicitações de acesso por Http
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Ativando a proteção contra usuario que não estão validados por token */
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        /* Ativando a permissão para acesso a pagina inicial do sistema: EX: sistema.com/index */

                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()

        /* URL DE LOGOUT*: Redireciona após o usuario deslogar do sistema. */
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

        /* Mapeia URL de logout e invalida o usuario. */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        /* Filtra requisições de login para autenticação */

        /* Filtra demais requisições para verificar a presença do token JWT no Header http  */

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //service que irá consultar usuario no banco de dados.
        auth.userDetailsService(implementacaoUserDetailsService)
                //Padrão de codificação de senhas
                .passwordEncoder(new BCryptPasswordEncoder());


    }
}
