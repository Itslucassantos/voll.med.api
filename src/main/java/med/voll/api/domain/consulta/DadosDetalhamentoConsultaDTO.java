package med.voll.api.domain.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosDetalhamentoConsultaDTO {

    private Long id;
    private Long idMedico;
    private Long idPaciente;
    private LocalDateTime data;

    public DadosDetalhamentoConsultaDTO(ConsultaEntity consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
