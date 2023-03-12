package med.voll.api.paciente;

import lombok.*;
import med.voll.api.endereco.DadosEnderecoDTO;
import javax.persistence.*;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private DadosEnderecoDTO endereco;

    public PacienteEntity(DadosCadastroPacienteDTO dados) {
        this.nome = dados.getNome();
        this.email = dados.getEmail();
        this.cpf = dados.getCpf();
        this.telefone = dados.getTelefone();
        this.endereco = dados.getEndereco();
    }

}
