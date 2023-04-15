package med.voll.api.domain.paciente;

import lombok.Getter;
import lombok.Setter;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DadosAtualizacaoPacienteDTO {

    @NotNull
    private Long id;
    private String nome;
    private String telefone;
    @Valid
    private DadosEnderecoDTO endereco;

}
