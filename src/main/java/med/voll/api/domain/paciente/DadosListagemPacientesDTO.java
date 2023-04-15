package med.voll.api.domain.paciente;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DadosListagemPacientesDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;

    public DadosListagemPacientesDTO(PacienteEntity paciente) {
        this(   paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf());
    }

    public DadosListagemPacientesDTO(Long id, String nome, String email, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }
}
