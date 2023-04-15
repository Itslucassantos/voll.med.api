package med.voll.api.domain.medico;

import lombok.*;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

import javax.persistence.*;

@Table(name = "medicos")
@Entity(name = "Medico")
/*como adicionei a dependencia do lombok, não precisa digitar getter, setter, construtor, pq ele ja faz isso
 *só basta usar as anotações como abaixo */
@Getter
@Setter
//construtor vazio
@NoArgsConstructor
//construtor com todos os parâmetros
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class MedicoEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private DadosEnderecoDTO endereco;
	private Boolean ativo;

	public MedicoEntity(DadosCadastroMedicoDTO dados) {
		this.ativo = true;
		this.nome = dados.getNome();
		this.email = dados.getEmail();
		this.telefone = dados.getTelefone();
		this.crm = dados.getCrm();
		this.especialidade = dados.getEspecialidade();
		this.endereco = new DadosEnderecoDTO(dados.getEndereco());
	}

	public void atualizarInformacoes(DadosAtualizacaoMedicoTDO dados){

		if(dados.getNome() != null) { this.nome = dados.getNome(); }

		if(dados.getTelefone() != null) { this.telefone = dados.getTelefone(); }

		if(dados.getEndereco() != null) { this.endereco.atualizaInformacoes(dados.getEndereco()); }
	}

	public void excluir() {
		this.ativo = false;
	}
}
