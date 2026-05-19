package clinica.servico;

/**
 * Acrescenta R$ 50,00 pelo deslocamento até o endereço do tutor.
 *
 * Padrão: Decorator (concreto)
 */
public class TaxaAtendimentoDomiciliar extends DecoradorServico {

    private static final double TAXA = 50.00;

    public TaxaAtendimentoDomiciliar(CalculoServico interno) {
        super(interno);
    }

    @Override
    public double calcularValor() {
        return interno.calcularValor() + TAXA;
    }

    @Override
    public String descrever() {
        return interno.descrever()
            + String.format("%n  + Taxa atendimento domiciliar: +R$ %.2f", TAXA);
    }
}
