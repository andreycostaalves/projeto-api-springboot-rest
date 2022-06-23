package curso.api.rest.security;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import projeto.spring.api.projetospringapi.ApplicationContextLoad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {
    /* tempo de validade do token */
    private static final long EXPIRATION_TIME = 172800000; /* 2 dias */

    /* Senha única para compor a autenticação*/
    private static final String SECRET = "SenhaExtremamenteSecreta";

    /* Prefixo padrão de token */
    private static final String TOKEN_PREFIX = "Bearer";


    private static final  String HEADER_STRING ="Authorization";


    /* Gerando token de autenticação e adicionando ao cabeçalho e resposta Http */
    public void addAuthentication(HttpServletResponse response, String username )throws IOException{

        /**Montagem do token */
        String JWT = Jwts.builder()  /**Chama o gerador de token*/
                .setSubject(username)  /** Adiciona o usuario*/
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /**Tempo de Expiração*/
                .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /** Compactação e algoritimo de geração de senha.*/

        /** JUNTA O TOKEN COM O PREFIXO.*/
        String token = TOKEN_PREFIX + " " + JWT;

        /** Adiciona no cabeçalho http */
        response.addHeader(HEADER_STRING, token);

        /** Escrevendo  token como resposta no corpo http*/
        response.getWriter().write("{\"Authorization\""+token+"\"}");

    }

    /**Retorna o usuario validado com o token ou caso não seja validade retorna null */
    public Authentication getAuthentication(HttpServletRequest request){

        /**Pega o token enviado no cabeçalho http*/
        String token = request.getHeader(HEADER_STRING);

        if(token != null){
            /**Faz a validação do token do usuario na requisição.*/
            String user =Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))     /**retira o token prefix do body*/
                    .getBody().getSubject();
            if(user != null){
                Usuario usuario  = ApplicationContextLoad.getApplicationContext()
                        .getBean(UsuarioRepository.class).findUserByLogin(user);
                /**Retorna o usuario logado.*/

                if(usuario != null){
                    return new UsernamePasswordAuthenticationToken(usuario.getLogin(),
                            usuario.getSenha(),
                            usuario.getAuthorities());
                }
            }
        }

        return null; /**Não autorizado*/


    }



}
