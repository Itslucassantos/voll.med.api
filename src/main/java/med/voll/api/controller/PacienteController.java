package med.voll.api.controller;

import lombok.var;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/pacientes")
//@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    private final PacienteRepository repository;

    @Autowired
    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPacienteDTO dados, UriComponentsBuilder uriBuilder){
        PacienteEntity paciente = new PacienteEntity(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity <Page<DadosListagemPacientesDTO>>
    listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginaçao) {
        Page<DadosListagemPacientesDTO> page = repository.
                findAllByAtivoTrue(paginaçao).map(DadosListagemPacientesDTO::new);
        return ResponseEntity.ok(page);
    }

    //Outra forma de retornar a lista, mas sem paginacao
//    @GetMapping
//    public List<DadosListagemPacientesDTO> listar(){
//        return repository.findAll().stream().map(DadosListagemPacientesDTO::new)
//                .collect(Collectors.toList());
//    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPacienteDTO dados) {
        PacienteEntity paciente = repository.getReferenceById(dados.getId());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        PacienteEntity paciente = repository.getReferenceById(id);
        paciente.exluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        PacienteEntity paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPacienteDTO(paciente));
    }



}
