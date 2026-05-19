package clinica.notificacao;

import clinica.atendimento.Atendimento;

/**
 * Notifica a recepção quando um atendimento é finalizado,
 * para que possam emitir a nota fiscal e liberar a sala.
 *
 * Padrão: Observer (observador concreto)
 * Canal de envio injetado via construtor (Bridge).
 */
public class AvisoRecepcao implements ObservadorAtendimento {

    private final CanalNotificacao canal;

    public AvisoRecepcao(CanalNotificacao canal) {
        this.canal = canal;
    }

    @Override
    public void atualizar(Atendimento atendimento, String evento) {
        if ("FINALIZADO".equals(evento)) {
            String destinatario = "Recepção";
            String mensagem = String.format(
                "Atendimento #%s finalizado. Paciente: %s | Serviço: %s | Tutor: %s — favor emitir nota fiscal.",
                atendimento.getId(),
                atendimento.getAnimal().getNome(),
                atendimento.getServico().getDescricao(),
                atendimento.getTutor().getNome()
            );
            canal.enviar(destinatario, mensagem);
        }
    }
}
