package med.voll.api.paciente;

import med.voll.api.endereco.DadosEnderecoDTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DadosCadastroPacienteDTO {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = " CPF inválido! ")
    @Pattern(regexp = "\\d{11}")
    private String cpf;
    @NotBlank
    private String telefone;

    @NotNull @Valid
    private DadosEnderecoDTO endereco;

    public DadosCadastroPacienteDTO() {}

    public DadosCadastroPacienteDTO(String nome, String email, String cpf, String telefone,
                                    DadosEnderecoDTO endereco) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public DadosEnderecoDTO getEndereco() { return endereco; }

    public void setNome(String nome) { this.nome = nome; }

    public void setEmail(String email) { this.email = email; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public void setEndereco(DadosEnderecoDTO endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "DadosCadastroPacienteDTO{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco=" + endereco +
                '}';
    }

}
