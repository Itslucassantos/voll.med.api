package med.voll.api.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosErroValidacaoDTO {

    private String campo;
    private String menssagem;

    public  DadosErroValidacaoDTO(FieldError error){
        this( error.getField(), error.getDefaultMessage());

    }

}
