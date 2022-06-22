package curso.api.rest.controller;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id){

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Usuario>>  BuscaTodos() {
        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);

    }
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> Cadastrar(@RequestBody Usuario usuario) {

        for(int pos = 0; pos < usuario.getTelefones().size(); pos++){
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }
        Usuario  usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> AlteraUsuario(@RequestBody Usuario usuario){
        if(usuario.getId() == null){
            return new ResponseEntity("ID não foi passado como parametro.",HttpStatus.BAD_REQUEST);
        }

        for(int pos = 0; pos < usuario.getTelefones().size(); pos++){
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        Usuario usuarioAlterado = usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(usuarioAlterado, HttpStatus.OK);

    }


    @DeleteMapping(value = "/{id}", produces = "application/text")
    public ResponseEntity<Usuario> DeletarUsuario(@PathVariable(value = "id") Long id){
        usuarioRepository.deleteById(id);
        return new ResponseEntity("Usuario Deletado.", HttpStatus.OK);
    }

}
