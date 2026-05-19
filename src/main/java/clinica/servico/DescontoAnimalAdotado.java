package clinica.servico;

/**
 * Aplica 15% de desconto para animais resgatados/adotados,
 * como incentivo à adoção responsável.
 *
 * Padrão: Decorator (concreto)
 */
public class DescontoAnimalAdotado extends DecoradorServico {

    private static final double PERCENTUAL_DESCONTO = 0.15;

    public DescontoAnimalAdotado(CalculoServico interno) {
        super(interno);
    }

    @Override
    public double calcularValor() {
        double valorAnterior = interno.calcularValor();
        return valorAnterior - (valorAnterior * PERCENTUAL_DESCONTO);
    }

    @Override
    public String descrever() {
        return interno.descrever()
            + String.format("%n  - Desconto animal adotado (15%%): -R$ %.2f",
                interno.calcularValor() * PERCENTUAL_DESCONTO);
    }
}
