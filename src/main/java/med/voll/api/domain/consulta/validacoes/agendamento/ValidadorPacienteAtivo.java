package med.voll.api.domain.consulta.validacoes.agendamento;

import lombok.var;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados){
        var pacienteEstaAtivo = repository.findAtivoById(dados.getIdPaciente());
        if(!pacienteEstaAtivo){
            throw new ValidacaoException(" Consulta não pode ser agendada com paciente excluído! ");
        }


    }


}
