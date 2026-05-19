package clinica.notificacao;

import clinica.atendimento.Atendimento;

/**
 * Notifica o veterinário quando um atendimento é cancelado.
 *
 * Padrão: Observer (observador concreto)
 * Canal de envio injetado via construtor (Bridge).
 */
public class AvisoVeterinario implements ObservadorAtendimento {

    private final CanalNotificacao canal;

    public AvisoVeterinario(CanalNotificacao canal) {
        this.canal = canal;
    }

    @Override
    public void atualizar(Atendimento atendimento, String evento) {
        if ("CANCELADO".equals(evento)) {
            String destinatario = atendimento.getVeterinario().getNome();
            String mensagem = String.format(
                "Aviso: o atendimento #%s do animal %s (tutor: %s) foi cancelado.",
                atendimento.getId(),
                atendimento.getAnimal().getNome(),
                atendimento.getTutor().getNome()
            );
            canal.enviar(destinatario, mensagem);
        }
    }
}
