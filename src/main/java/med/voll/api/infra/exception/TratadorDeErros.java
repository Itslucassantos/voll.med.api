package med.voll.api.infra.exception;

import lombok.var;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.infra.exception.DadosErroValidacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

//@RestControllerAdvice é pra dizer para o spring que essa classe é de tratativas de erro, por isso, usa-se essa anotação.
// Em APIs Rest, classes de tratamento de exceptions devem ser anotadas com o @RestControllerAdvice
// e não com o @RestController
@RestControllerAdvice
public class TratadorDeErros {

    // Quando der esse erro EntityNotFoundException o Spring chama esse método 404
    // @ExceptionHandler é pra podermos dizer qual exceção vms tratar.
    // EntityNotFoundException.class, diz quando o id passado não está no BD, não existe.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    //Quando tem algum campo inválido é lançado esse erro MethodArgumentNotValidException.class
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //recebe a excecao para ver os campos que está errado e retornar
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacaoDTO::new).collect(Collectors.toList()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
