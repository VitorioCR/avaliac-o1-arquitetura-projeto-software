package clinica.notificacao;

/**
 * Canal que registra avisos no console (log).
 * Útil para testes e ambientes de desenvolvimento.
 *
 * Padrão: Bridge (Implementação concreta do CanalNotificacao)
 */
public class CanalLog implements CanalNotificacao {

    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.printf("[LOG] → %s: %s%n", destinatario, mensagem);
    }
}
