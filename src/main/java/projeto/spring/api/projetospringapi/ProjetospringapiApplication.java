package projeto.spring.api.projetospringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = {"curso.api.rest.model"}) //Aponta a pasta de models.
@ComponentScan(basePackages = {"curso.api.rest"}) //Aponta todos os pacotes.
@EnableJpaRepositories(basePackages = {"curso.api.rest.repository"}) //Aponta para pasta de repositorios.
@EnableTransactionManagement //controlla transações no banco de dados.
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class ProjetospringapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetospringapiApplication.class, args);
	}

}
