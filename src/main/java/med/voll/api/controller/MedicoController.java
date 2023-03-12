package med.voll.api.controller;

import med.voll.api.medico.DadosListagemMedicosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import med.voll.api.medico.DadosCadastroMedicoDTO;
import med.voll.api.medico.MedicoEntity;
import med.voll.api.medico.MedicoRepository;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	/*fazendo uma injeção, igual declarar seu dao para poder usar, usando a anotação @Autowired o spring
	 *  faz a injeção de dependência, vc diz pro Spring que ele vai instanciar e passar esse atributo repository
	 *  dentro da classe controller */
	@Autowired
	private MedicoRepository repository;
	
	/* Quando chamar a url: /medicos enviando uma requisão do tipo Post, ou seja, enviando dados
	 * é para acessar o método cadastrar, pq é uma requisisão post, por isso a anotação @PostMapping
	 * que é pra acessar o método cadastrar e salvar a requisição. */
	@PostMapping
	/* @Transactional para ter uma transação ativa com o banco de dados, 
	 * para assim, inserir os dados no banco. */
	@Transactional
	//Usando a anotação @RequestBody pq é pra pegar os dados do corpo do json, ou seja, todas as informações.
	//Sempre tem que colocar o @Valid, sem essa anotação o Spring não vai disparar o processo de
	// validação do Bean Validation.
	public void cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados) {
		/*como está herdando da interface JpaRepository é como se fosse o Dao, 
		 * tenho um CRUD para usar e nesse caso vamos usar o savar*/
		/* cria uma entitade médico para receber os dados da DTO dados, para isso é só criar um construtor
		no MedicoEntity que receba dados*/
		repository.save(new MedicoEntity(dados));
	}
	//@GetMapping pq está devolvendo uma lista para api e não recebendo dados.
	//Pageable é pra ordenar o retorno dos dados, por sequecia alfabetica, etc
	//@PageableDefault, com essa anotação dá pra ordenar a requisição de 10 em 10 e
	//ordenar por nome em ordem alfabética.
	@GetMapping
	public Page<DadosListagemMedicosDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
		return repository.findAll(paginacao).map(DadosListagemMedicosDTO::new);
	}

}
