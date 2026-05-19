package clinica.atendimento;

/**
 * Define as transições de situação permitidas para um atendimento.
 * Cada situação concreta decide o que pode ou não acontecer — sem if/switch no Atendimento.
 *
 * Padrão: State
 */
public interface SituacaoAtendimento {

    void iniciar(Atendimento atendimento);

    void finalizar(Atendimento atendimento);

    void cancelar(Atendimento atendimento);

    String getNome();
}
