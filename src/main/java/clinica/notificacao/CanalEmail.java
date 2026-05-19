package clinica.notificacao;

/**
 * Canal que simula o envio de e-mail.
 * Em produção, aqui entraria o cliente SMTP real.
 *
 * Padrão: Bridge (Implementação concreta do CanalNotificacao)
 */
public class CanalEmail implements CanalNotificacao {

    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.printf("[EMAIL] → %s: %s%n", destinatario, mensagem);
    }
}
