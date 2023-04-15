package med.voll.api.domain.paciente;

import lombok.*;
import med.voll.api.domain.endereco.DadosEnderecoDTO;
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
    private Boolean ativo;

    public PacienteEntity(DadosCadastroPacienteDTO dados) {
        this.ativo = true;
        this.nome = dados.getNome();
        this.email = dados.getEmail();
        this.cpf = dados.getCpf();
        this.telefone = dados.getTelefone();
        this.endereco = new DadosEnderecoDTO(dados.getEndereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoPacienteDTO dados) {

        if(dados.getNome() != null) { this.nome = dados.getNome(); }

        if(dados.getTelefone() != null) { this.telefone = dados.getTelefone(); }

        if(dados.getEndereco() != null) { this.endereco.atualizaInformacoes(dados.getEndereco());}

    }

    public void exluir() {
        this.ativo = false;
    }
}
