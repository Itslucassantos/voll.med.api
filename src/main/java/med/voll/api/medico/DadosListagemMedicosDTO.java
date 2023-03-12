package med.voll.api.medico;

public class DadosListagemMedicosDTO {

    private String nome;
    private String email;
    private String crm;
    private Especialidade especialidade;

    public DadosListagemMedicosDTO(MedicoEntity medico) {
        this(   medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade());
    }

    public DadosListagemMedicosDTO(String nome, String email, String crm, Especialidade especialidade) {
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getCrm() {
        return crm;
    }
    public Especialidade getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "DadosListagemMedicosDTO{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade=" + especialidade +
                '}';
    }
}
