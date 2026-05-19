package clinica.atendimento;

/**
 * Situação inicial do atendimento.
 * Permite transição para EmAtendimento ou Cancelado.
 *
 * Padrão: State (estado concreto)
 */
public class SituacaoAgendado implements SituacaoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        atendimento.setSituacao(new SituacaoEmAtendimento());
        atendimento.notificarObservadores("INICIADO");
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        throw new IllegalStateException(
            "Não é possível finalizar um atendimento que ainda está Agendado."
        );
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        atendimento.setSituacao(new SituacaoCancelado());
        atendimento.notificarObservadores("CANCELADO");
    }

    @Override
    public String getNome() {
        return "Agendado";
    }
}
