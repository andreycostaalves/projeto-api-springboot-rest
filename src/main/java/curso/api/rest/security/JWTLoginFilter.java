package curso.api.rest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import curso.api.rest.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**ESTABELECE NOSSO GERENTE DE TOKEN*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**CONFIGURANDO O GERENCIADOR DE TOKEN*/
    protected JWTLoginFilter(String url, AuthenticationManager AuthenticationManager) {
        /**OBRIGA A AUTENTICAR A URL*/
        super(new AntPathRequestMatcher(url));


        /**GERENCIADOR DE AUTENTICAÇÃO*/
        setAuthenticationManager(AuthenticationManager);

    }
    /**Retorna o usuario ao processar a autenticação*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /**Está pegando o token para validar*/
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        /**Retorna usuario login senha e acessos*/
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
    }
}
