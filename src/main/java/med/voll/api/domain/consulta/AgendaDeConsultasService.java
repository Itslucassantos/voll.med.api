package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacoes.agendamento.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.MedicoEntity;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteEntity;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// OBS: Mas é importante ficarmos atentos, pois muitas vezes não é necessário criar um Service e,
// consequentemente, adicionar mais uma camada e complexidade desnecessária à nossa aplicação.
// Uma regra que podemos utilizar é a seguinte: se não houverem regras de negócio, podemos simplesmente realizar
// a comunicação direta entre os controllers e os repositories da aplicação.Mas é importante ficarmos atentos,
// pois muitas vezes não é necessário criar um Service e, consequentemente, adicionar mais uma camada e
// complexidade desnecessária à nossa aplicação. Uma regra que podemos utilizar é a seguinte:
// se não houverem regras de negócio, podemos simplesmente realizar a comunicação direta entre os controllers e
// os repositories da aplicação.

@Service
public class AgendaDeConsultasService {

    //para salvar o agendamento da consulta no Banco de Dados, é preciso fazer uma
    //injeção de dependência do ConsultaRepository.
    @Autowired
    private ConsultaRepository consultaRepository;

    //Para carregar o medico e o paciente, ou seja, trazer os dados daquele medico e paciente, nesse caso o id,
    //é preciso fazer a injeção de dependência.
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsultaDTO agendar(DadosAgendamentoConsultaDTO dados){
        //se não existir o paciente com esse id, interrompe. "!", usado para negar. O mesmo para o medico.
        if(!pacienteRepository.existsById(dados.getIdPaciente())){
            throw new ValidacaoException(" Id do paciente informado não existe! ");
        }
        // medico pode ser qualquer um nesse caso.
        if(dados.getIdMedico() != null && !medicoRepository.existsById(dados.getIdMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        PacienteEntity paciente = pacienteRepository.getReferenceById(dados.getIdPaciente());
        //como o médico é opcional, ele pode vim null, ent criei o escolherMedico(), para pegar um medico aleatório.
        MedicoEntity medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        ConsultaEntity consulta = new ConsultaEntity(null, medico, paciente, dados.getData(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsultaDTO(consulta);
    }

    private MedicoEntity escolherMedico(DadosAgendamentoConsultaDTO dados) {
        //se esse médico estiver vindo do Banco de Dados, carregue ele.
        if(dados.getIdMedico() != null){
            return medicoRepository.getReferenceById(dados.getIdMedico());
        }
        // Agora se esse médico não estiver vindo, precisa passar a especialidade do médico que deseja fazer a consulta,
        // para assim, poder escolher um médico.
        if(dados.getEspecialidade() == null){
            throw new ValidacaoException(" Especialidade é obrigatória quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.getEspecialidade(), dados.getData());
    }

    public void cancelar(DadosCancelamentoConsultaDTO dados) {
        if(!consultaRepository.existsById(dados.getIdConsulta())){
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        ConsultaEntity consulta = consultaRepository.getReferenceById(dados.getIdConsulta());
        consulta.cancelar(dados.getMotivo());

    }


}
