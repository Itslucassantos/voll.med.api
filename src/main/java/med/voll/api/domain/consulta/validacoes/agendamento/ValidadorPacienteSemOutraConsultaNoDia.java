package med.voll.api.domain.consulta.validacoes.agendamento;

import lombok.var;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        LocalDateTime primeiroHorario = dados.getData().withHour(7);
        LocalDateTime ultimoHorario = dados.getData().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.
                existsByPacienteIdAndDataBetween(dados.getIdPaciente(), primeiroHorario, ultimoHorario);
        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException(" Paciente já possui uma consulta agendada nesse dia! ");
        }
    }

}
