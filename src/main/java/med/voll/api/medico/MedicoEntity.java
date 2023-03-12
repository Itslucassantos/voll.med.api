package med.voll.api.medico;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.endereco.DadosEnderecoDTO;

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

	public MedicoEntity(DadosCadastroMedicoDTO dados) {

		this.nome = dados.getNome();
		this.email = dados.getEmail();
		this.telefone = dados.getTelefone();
		this.crm = dados.getCrm();
		this.especialidade = dados.getEspecialidade();
		this.endereco = new DadosEnderecoDTO(dados.getEndereco());
	}

}
