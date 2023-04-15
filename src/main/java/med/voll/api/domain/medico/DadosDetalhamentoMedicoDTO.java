package med.voll.api.domain.medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosDetalhamentoMedicoDTO {

    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Especialidade especialidade;
    private DadosEnderecoDTO endereco;

    public DadosDetalhamentoMedicoDTO(MedicoEntity medico) {
        this(   medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade(),
                medico.getEndereco());
    }

}
