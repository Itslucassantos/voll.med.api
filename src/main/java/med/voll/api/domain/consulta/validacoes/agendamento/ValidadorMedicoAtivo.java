package med.voll.api.domain.consulta.validacoes.agendamento;

import lombok.var;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        // escolha do médico opcional
        if(dados.getIdMedico() == null){
            //pula fora daí
            return;
        }
        var medicoEstaAtivo = repository.findAtivoById(dados.getIdMedico());
        if(!medicoEstaAtivo){
            throw new ValidacaoException(" Consulta não pode ser agendada com médico ");
        }
    }





}
