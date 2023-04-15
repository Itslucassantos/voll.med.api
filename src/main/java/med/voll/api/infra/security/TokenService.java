package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import med.voll.api.domain.usuario.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;

@Service
public class TokenService {

    //@Value("${api.security.token.secret}") é para pegar as configurações feitas no
    // meu application.properties e usá-las.
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioEntity usuario){
        try {
            //"secret" é a senha passsada para poder gerar token.
            //.withClaim(), vc pode adicionar qualquer informação que queira.
            var algoritmo = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withIssuer("API Voll.med")
                    //identifica o login do usuario ao qual esse token pertence.
                    .withSubject(usuario.getLogin())
                    //tempo de duração que o token será válido.
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }
    }

    // agr precisa chamar esse método na classe securityFilter, então tem que pedir pro Spring injetar essa classe,
    // criando uma injeção de dependência na securityFilter com @Autowired
    // olha se o token é válido e devolve o usuário, o subject que está armazenado dentro do tokenJWT.
    public String getSubject(String tokenJWT){
        //faz a divisão do token "split" pelo ponto, com isso, a gente obtem um array de String.
        // é um chunks de três posições, tem o header, payload, verify signature.
        String[] chunks = tokenJWT.split("\\.");
        //Base64 é o tipo de criptografia do token.
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        try {
            //HashMap.class é quem faz toda a decodificação para um mapa.
            HashMap payloadMap = new ObjectMapper().readValue(payload, HashMap.class);

            if(payloadMap.get("iss").equals("API Voll.med")) {
                return (String) payloadMap.get("sub");
            } else {
                throw new RuntimeException("Token JWT inválido ou expirado!");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        try {
//            var algoritmo = Algorithm.HMAC256(secret);
//            return JWT.require(algoritmo)
//                    .withIssuer("API Voll.med")
//                    .build()
//                    //verifica se o token está válido de acordo com os parâmetros passados acima.
//                    .verify(tokenJWT)
//                    .getSubject();
//        } catch (JWTVerificationException exception) {
//            throw new RuntimeException("Token JWT inválido ou expirado!");
//        }

    }

    // método criado para a data de duração do token, por quanto tempo ele será válido.
    private Instant dataExpiracao() {
        // .toInstant(ZoneOffset.of("-03:00") é para passar o fuso horário.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
