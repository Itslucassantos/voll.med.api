package med.voll.api.domain.medico;

import lombok.*;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DadosAtualizacaoMedicoTDO {

    @NotNull
    private Long id;
    private String nome;
    private String telefone;
    private DadosEnderecoDTO endereco;

}
