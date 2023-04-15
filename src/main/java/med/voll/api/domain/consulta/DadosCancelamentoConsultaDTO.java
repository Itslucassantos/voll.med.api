package med.voll.api.domain.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosCancelamentoConsultaDTO {

    @NotNull
    private Long idConsulta;

    @NotNull
    private MotivoCancelamento motivo;

}
