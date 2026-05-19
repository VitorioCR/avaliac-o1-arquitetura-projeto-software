package clinica.notificacao;

import clinica.atendimento.Atendimento;

/**
 * Contrato para qualquer parte interessada em receber avisos
 * quando a situação de um atendimento muda.
 *
 * Padrão: Observer
 */
public interface ObservadorAtendimento {

    /**
     * @param atendimento o atendimento cujo estado mudou
     * @param evento      descrição do que aconteceu (ex.: "INICIADO", "CANCELADO", "FINALIZADO")
     */
    void atualizar(Atendimento atendimento, String evento);
}
