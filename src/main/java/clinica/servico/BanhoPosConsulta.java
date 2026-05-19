package clinica.servico;

/**
 * Acrescenta R$ 30,00 pelo serviço de banho realizado após a consulta.
 *
 * Padrão: Decorator (concreto)
 */
public class BanhoPosConsulta extends DecoradorServico {

    private static final double VALOR_BANHO = 30.00;

    public BanhoPosConsulta(CalculoServico interno) {
        super(interno);
    }

    @Override
    public double calcularValor() {
        return interno.calcularValor() + VALOR_BANHO;
    }

    @Override
    public String descrever() {
        return interno.descrever()
            + String.format("%n  + Banho pós-consulta: +R$ %.2f", VALOR_BANHO);
    }
}
