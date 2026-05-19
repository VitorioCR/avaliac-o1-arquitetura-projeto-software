package clinica.atendimento;

/**
 * Atendimento encerrado com sucesso.
 * Estado terminal — nenhuma transição é permitida.
 *
 * Padrão: State (estado concreto)
 */
public class SituacaoFinalizado implements SituacaoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        throw new IllegalStateException(
            "O atendimento já foi Finalizado e não pode ser reaberto."
        );
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        throw new IllegalStateException(
            "O atendimento já foi Finalizado."
        );
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        throw new IllegalStateException(
            "Não é possível cancelar um atendimento já Finalizado."
        );
    }

    @Override
    public String getNome() {
        return "Finalizado";
    }
}
