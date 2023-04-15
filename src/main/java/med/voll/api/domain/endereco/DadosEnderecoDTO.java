package med.voll.api.domain.endereco;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
//construtor vazio
@NoArgsConstructor
//construtor com todos os parâmetros
@AllArgsConstructor
public class DadosEnderecoDTO {
	@NotBlank
	private String logradouro;
	@NotBlank
	private String bairro;
	@NotBlank
	@Pattern(regexp = "\\d{8}")
	private String cep;
	@NotBlank
	private String cidade;
	@NotBlank
	private String uf;
	private String complemento;
	private String numero;
	
	public DadosEnderecoDTO(DadosEnderecoDTO dados) {
		this.logradouro = dados.getLogradouro();
		this.bairro = dados.getBairro();
		this.cep = dados.getCep();
		this.cidade = dados.getCidade();
		this.uf = dados.getUf();
		this.complemento = dados.getComplemento();
		this.numero = dados.getNumero();
	}

    public void atualizaInformacoes(DadosEnderecoDTO dados) {

		if (dados.getLogradouro() != null) { this.logradouro = dados.getLogradouro(); }

		if (dados.getBairro() != null) { this.bairro = dados.getBairro(); }

		if (dados.getCep() != null) { this.cep = dados.getCep(); }

		if (dados.getUf() != null) { this.uf = dados.getUf(); }

		if (dados.getCidade() != null) { this.cidade = dados.getCidade(); }

		if (dados.getNumero() != null) { this.numero = dados.getNumero(); }

		if (dados.getComplemento() != null) { this.complemento = dados.getComplemento(); }

    }

//	public DadosEndereco() {}
//
//	public DadosEndereco(String logradouro, String bairro, String cep, String cidade, String uf, String complemento,
//			String numero) {
//		super();
//		this.logradouro = logradouro;
//		this.bairro = bairro;
//		this.cep = cep;
//		this.cidade = cidade;
//		this.uf = uf;
//		this.complemento = complemento;
//		this.numero = numero;
//	}
//	public String getLogradouro() {
//		return logradouro;
//	}
//	public void setLogradouro(String logradouro) {
//		this.logradouro = logradouro;
//	}
//	public String getBairro() {
//		return bairro;
//	}
//	public void setBairro(String bairro) {
//		this.bairro = bairro;
//	}
//	public String getCep() {
//		return cep;
//	}
//	public void setCep(String cep) {
//		this.cep = cep;
//	}
//	public String getCidade() {
//		return cidade;
//	}
//	public void setCidade(String cidade) {
//		this.cidade = cidade;
//	}
//	public String getUf() {
//		return uf;
//	}
//	public void setUf(String uf) {
//		this.uf = uf;
//	}
//	public String getComplemento() {
//		return complemento;
//	}
//	public void setComplemento(String complemento) {
//		this.complemento = complemento;
//	}
//	public String getNumero() {
//		return numero;
//	}
//	public void setNumero(String numero) {
//		this.numero = numero;
//	}
//
//	@Override
//	public String toString() {
//		return "DadosEndereco [logradouro=" + logradouro + ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade
//				+ ", uf=" + uf + ", complemento=" + complemento + ", numero=" + numero + "]";
//	}

}
