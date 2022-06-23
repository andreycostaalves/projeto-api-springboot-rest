package curso.api.rest.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role")
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)
public class Role implements GrantedAuthority {
      private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    private Long id;

    private String nomeRole;            /*Papel. exemplo: ROLE_SECRETARIO OU ROLE_GERENTE...*/


    @Override
    public String getAuthority() {       /*RETORNA O NOME DO PAPEL, ACESSO OU AUTORIZAÇÃO, EX: ROLE_GERENTE*/
        return this.nomeRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRole() {
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }
}
