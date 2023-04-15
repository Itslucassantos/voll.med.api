package med.voll.api.controller;

import lombok.var;
import med.voll.api.domain.medico.*;
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
	// Quando criado um método cadastrar, deve retornar um código 201 das boas práticas do http, que é o create.
	//Porém, esse código 201 possui algumas regras. Na resposta, devemos colocar o código 201 e no corpo da
	// resposta os dados do novo registro criado e, também, um cabeçalho do protocolo HTTP.
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados, UriComponentsBuilder uriBuilder) {
		/*como está herdando da interface JpaRepository é como se fosse o Dao, 
		 * tenho um CRUD para usar e nesse caso vamos usar o savar*/
		/* cria uma entitade médico para receber os dados da DTO dados, para isso é só criar um construtor
		no MedicoEntity que receba dados*/

		MedicoEntity medico = new MedicoEntity(dados);
		repository.save(medico);

		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicoDTO());
	}
	//@GetMapping pq está devolvendo uma lista para api e não recebendo dados.
	//Pageable é pra ordenar o retorno dos dados, por sequecia alfabetica, etc
	//@PageableDefault, com essa anotação dá pra ordenar a requisição de 10 em 10 e
	//ordenar por nome em ordem alfabética.
	@GetMapping
	public ResponseEntity <Page<DadosListagemMedicosDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
		Page<DadosListagemMedicosDTO> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicosDTO::new);
		//ok devolve o código 200, nesse caso, junto com a lista.
		return ResponseEntity.ok(page);
	}

	//@PutMapping é usado para fazer atualização, é parecido com o post, está enviando uma requisição com dados
	//para fazer a atualização no banco de dados.
	@PutMapping
	@Transactional
	// Seguindo o protocolo http, o método atualizar é interessante devolver os dados que foram atualizados,
	// então devemos devolver um ok junto com o DTO com todas as informações do médico.
	// criar um novo DTO de atualizacao de informacao.
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicoTDO dados){
		MedicoEntity medico = repository.getReferenceById(dados.getId());
		medico.atualizarInformacoes(dados);

		return ResponseEntity.ok(new DadosDetalhamentoMedicoDTO(medico));
	}
	/* Esse Delete apaga tudo do banco de dados.
// colocar entre chaves {} é para o spring entender que é um parâmetro dinâmico, ou seja ele muda.
	@DeleteMapping("/{id}")
	@Transactional
	//@PathVariable para dizer que é uma variável da url, q nesse caso é o id, se nn ele nn entendi.
	public void excluir(@PathVariable Long id){
		repository.deleteById(id);
	} */

	// Faz a exclusão lógica, não apaga do BD, somente deixa inativo
	// O método excluir tem que retornar o erro 204, q é o ok, seguindo as regras do protocolo http.
	// Para isso usamos a classe ResponseEntity do próprio Spring, ela tem diversos métodos estáticos
	// para fazer o retorno adequado.
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id){
		MedicoEntity medico = repository.getReferenceById(id);
		medico.excluir();
		return ResponseEntity.noContent().build();
	}

	// Detalha as informações do id pedido.
	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		MedicoEntity medico = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoMedicoDTO(medico));
	}

}
