package med.voll.api.domain.medico;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DadosListagemMedicosDTO {

    private Long id;
    private String nome;
    private String email;
    private String crm;
    private Especialidade especialidade;

    public DadosListagemMedicosDTO(MedicoEntity medico) {
        this(   medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade());
    }

    public DadosListagemMedicosDTO(Long id, String nome, String email, String crm, Especialidade especialidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.especialidade = especialidade;
    }

}
