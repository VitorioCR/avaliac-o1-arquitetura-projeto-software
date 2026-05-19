package clinica.servico;

/**
 * Classe base para todos os decoradores de cálculo.
 * Delega para o CalculoServico interno e permite que subclasses
 * adicionem ou subtraiam valores de forma encadeada.
 *
 * Padrão: Decorator (Decorator abstrato)
 */
public abstract class DecoradorServico implements CalculoServico {

    protected final CalculoServico interno;

    protected DecoradorServico(CalculoServico interno) {
        this.interno = interno;
    }

    @Override
    public double calcularValor() {
        return interno.calcularValor();
    }

    @Override
    public String descrever() {
        return interno.descrever();
    }
}
