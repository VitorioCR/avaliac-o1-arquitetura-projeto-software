package clinica.atendimento;

/**
 * Atendimento em andamento.
 * Permite apenas a transição para Finalizado.
 *
 * Padrão: State (estado concreto)
 */
public class SituacaoEmAtendimento implements SituacaoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        throw new IllegalStateException(
            "O atendimento já está em andamento."
        );
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        atendimento.setSituacao(new SituacaoFinalizado());
        atendimento.notificarObservadores("FINALIZADO");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        throw new IllegalStateException(
            "Não é possível cancelar um atendimento que já está Em Atendimento."
        );
    }

    @Override
    public String getNome() {
        return "EmAtendimento";
    }
}
