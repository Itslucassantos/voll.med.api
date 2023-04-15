package med.voll.api.domain.consulta.validacoes.agendamento;

import lombok.var;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados){
        var medicoComOutraConsultaNoMesmoHorario = repository.
                existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.getIdMedico(),
                dados.getData());
        if(medicoComOutraConsultaNoMesmoHorario){
          throw new ValidacaoException(" Médico já possui outra consulta agendada nesse mesmo horário! ");
        }
    }

}
