package med.voll.api.medico;

import med.voll.api.endereco.DadosEnderecoDTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DadosCadastroMedicoDTO {

	//@NotBlank verifica se não está null nem vazio, @Email verifica se é um email, @NotBlank é só para String
	//@Pattern(regexp = "\\d{4,6}") quis dizer que os numeros da crm tem q ser de 4,6 digitos
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String telefone;
	@NotBlank
	@Pattern(regexp = "\\d{4,6}")
	private String crm;
	private Especialidade especialidade;
	//@Valid é para validar tbm os atributos dentro do objeto endereco
	@NotNull @Valid
	private DadosEnderecoDTO endereco;
	
	public DadosCadastroMedicoDTO() {}
	
	public DadosCadastroMedicoDTO(String nome, String email, String telefone, String crm, Especialidade especialidade,
			DadosEnderecoDTO endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.crm = crm;
		this.especialidade = especialidade;
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	public DadosEnderecoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(DadosEnderecoDTO endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "DadosCadastroMedico [nome=" + nome + ", email=" + email + ", crm=" + crm + ", especialidade="
				+ especialidade + ", endereco=" + endereco + "]";
	}

}
