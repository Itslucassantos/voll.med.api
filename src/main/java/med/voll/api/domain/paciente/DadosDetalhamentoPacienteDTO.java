package med.voll.api.domain.paciente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosDetalhamentoPacienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private DadosEnderecoDTO endereco;

    public DadosDetalhamentoPacienteDTO(PacienteEntity paciente) {
        this(   paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEndereco());
    }


}
