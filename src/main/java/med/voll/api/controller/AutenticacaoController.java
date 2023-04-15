package med.voll.api.controller;

import lombok.var;
import med.voll.api.domain.usuario.DadosAutenticacaoDTO;
import med.voll.api.domain.usuario.UsuarioEntity;
import med.voll.api.infra.security.DadosTokenJWTDTO;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    //para poder validar o login, como estamos usando uma implementação no AutenticacaoService, ele está fznd a validação
    // por baixo dos banos, ent é preciso chamar a classe AuthenticationManager para fazer a conexão/validação.
    private AuthenticationManager manager;

    //Fznd a injeção de dependências para poder usar a classe chamada.
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados){

        //convertendo do nosso DTO para o DTO do UsernamePasswordAuthenticationToken
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getLogin(), dados.getSenha());
        var authentication = manager.authenticate(authenticationToken);
        //getPrincipal() é para pegar o usuário logado.
        var tokenJWT = tokenService.gerarToken((UsuarioEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
    }

}
