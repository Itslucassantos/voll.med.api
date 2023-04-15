package med.voll.api.domain.consulta.validacoes.agendamento;

import lombok.var;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

//poderia ser um @Service, pq é uma classe de service
//Nomeando pq tem outro validador no cancelamento com o msm nome, para não dar conflito nomeei no @Component
@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsultaDTO dados){
        LocalDateTime dataConsuta = dados.getData();
        LocalDateTime agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsuta).toMinutes();

        if(diferencaEmMinutos < 30){
            throw new ValidacaoException(" Consulta deve ser agendada com antecedência mínima de 30 minutos! ");
        }
    }


}
