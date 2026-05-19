package clinica.servico;

/**
 * Componente base do cálculo de valor de um atendimento.
 * Toda regra de preço (base ou adicional) implementa esta interface.
 *
 * Padrão: Decorator (Component)
 */
public interface CalculoServico {

    /** Retorna o valor calculado, com todas as regras aplicadas até aqui. */
    double calcularValor();

    /** Descrição legível da composição de regras aplicadas. */
    String descrever();
}
