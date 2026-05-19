package clinica.model;

/**
 * Representa um veterinário responsável pelo atendimento.
 */
public class Veterinario {

    private String nome;
    private String crmv;
    private String especialidade;

    public Veterinario(String nome, String crmv, String especialidade) {
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "Dr(a). " + nome + " | CRMV: " + crmv + " | " + especialidade;
    }
}
