package med.voll.api.domain.consulta.validacoes.agendamento.cancelamento;

import med.voll.api.domain.consulta.DadosCancelamentoConsultaDTO;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsultaDTO dados);

}
