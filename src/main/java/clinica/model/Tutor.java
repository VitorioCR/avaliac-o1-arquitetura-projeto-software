package clinica.model;

/**
 * Representa o tutor (dono) do animal atendido na clínica.
 */
public class Tutor {

    private String nome;
    private String telefone;
    private String email;

    public Tutor(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nome + " | tel: " + telefone + " | email: " + email;
    }
}
