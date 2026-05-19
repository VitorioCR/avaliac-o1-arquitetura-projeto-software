package clinica.model;

/**
 * Representa um animal atendido na clínica veterinária.
 */
public class Animal {

    private String nome;
    private String especie;
    private String raca;
    private int idadeAnos;
    private boolean adotado;

    public Animal(String nome, String especie, String raca, int idadeAnos, boolean adotado) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idadeAnos = idadeAnos;
        this.adotado = adotado;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdadeAnos() {
        return idadeAnos;
    }

    public boolean isAdotado() {
        return adotado;
    }

    @Override
    public String toString() {
        return nome + " (" + especie + "/" + raca + ")" + (adotado ? " [adotado]" : "");
    }
}
