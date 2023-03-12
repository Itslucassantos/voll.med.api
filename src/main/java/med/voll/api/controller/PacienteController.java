package med.voll.api.controller;

import med.voll.api.paciente.DadosCadastroPacienteDTO;
import med.voll.api.paciente.PacienteEntity;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    private void cadastrar(@RequestBody @Valid DadosCadastroPacienteDTO dados){
        repository.save(new PacienteEntity(dados));
    }
}
