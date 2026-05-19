package clinica.notificacao;

import clinica.atendimento.Atendimento;

/**
 * Notifica o tutor do animal quando o atendimento é iniciado.
 *
 * Padrão: Observer (observador concreto)
 * O canal de envio é injetado via construtor (Bridge),
 * permitindo trocar Log por Email sem alterar esta classe.
 */
public class AvisoTutor implements ObservadorAtendimento {

    private final CanalNotificacao canal;

    public AvisoTutor(CanalNotificacao canal) {
        this.canal = canal;
    }

    @Override
    public void atualizar(Atendimento atendimento, String evento) {
        if ("INICIADO".equals(evento)) {
            String destinatario = atendimento.getTutor().getNome();
            String mensagem = String.format(
                "Olá, %s! O atendimento do seu pet %s começou agora com %s.",
                atendimento.getTutor().getNome(),
                atendimento.getAnimal().getNome(),
                atendimento.getVeterinario().getNome()
            );
            canal.enviar(destinatario, mensagem);
        }
    }
}
