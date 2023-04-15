package med.voll.api.infra.security;

import lombok.Getter;

@Getter
public class DadosTokenJWTDTO {

    private String tokenJWT;
    public DadosTokenJWTDTO(String token) {
        //como não é uma classe record, criei uma variável que recebe o DTO, para assim, mandar no postman.
        this.tokenJWT = token;
    }
}
