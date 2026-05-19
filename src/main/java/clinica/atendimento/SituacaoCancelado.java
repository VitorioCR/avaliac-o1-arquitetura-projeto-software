package clinica.atendimento;

/**
 * Atendimento cancelado.
 * Estado terminal — nenhuma transição é permitida.
 *
 * Padrão: State (estado concreto)
 */
public class SituacaoCancelado implements SituacaoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        throw new IllegalStateException(
            "O atendimento foi Cancelado e não pode ser reiniciado."
        );
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        throw new IllegalStateException(
            "Não é possível finalizar um atendimento Cancelado."
        );
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        throw new IllegalStateException(
            "O atendimento já está Cancelado."
        );
    }

    @Override
    public String getNome() {
        return "Cancelado";
    }
}
