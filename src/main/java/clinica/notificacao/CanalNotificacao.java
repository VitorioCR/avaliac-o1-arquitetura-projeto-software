package clinica.notificacao;

/**
 * Define como uma mensagem deve ser enviada, independente
 * de quem está enviando ou do conteúdo do negócio.
 *
 * Padrão: Bridge (Implementor)
 * — separa "o que avisar" (Observer) de "como avisar" (este canal).
 */
public interface CanalNotificacao {

    /**
     * @param destinatario nome ou endereço de quem receberá a mensagem
     * @param mensagem     conteúdo do aviso
     */
    void enviar(String destinatario, String mensagem);
}
