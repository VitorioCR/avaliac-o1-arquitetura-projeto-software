package clinica.servico;

/**
 * Tipos de serviço veterinário disponíveis na clínica,
 * cada um com seu valor base de referência.
 */
public enum ServicoVeterinario {

    CONSULTA("Consulta", 120.00),
    CIRURGIA("Cirurgia", 800.00),
    VACINACAO("Vacinação", 80.00),
    EXAME("Exame Laboratorial", 150.00),
    RETORNO("Retorno de Consulta", 60.00);

    private final String descricao;
    private final double valorBase;

    ServicoVeterinario(String descricao, double valorBase) {
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorBase() {
        return valorBase;
    }

    @Override
    public String toString() {
        return descricao + " - R$ " + String.format("%.2f", valorBase);
    }
}
