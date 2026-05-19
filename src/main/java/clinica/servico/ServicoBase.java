package clinica.servico;

/**
 * Valor base do serviço veterinário, sem nenhuma regra adicional.
 * É o ponto de partida da cadeia de Decoradores.
 *
 * Padrão: Decorator (Concrete Component)
 */
public class ServicoBase implements CalculoServico {

    private final ServicoVeterinario servico;

    public ServicoBase(ServicoVeterinario servico) {
        this.servico = servico;
    }

    @Override
    public double calcularValor() {
        return servico.getValorBase();
    }

    @Override
    public String descrever() {
        return String.format("%s: R$ %.2f", servico.getDescricao(), servico.getValorBase());
    }
}
