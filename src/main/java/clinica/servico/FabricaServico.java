package clinica.servico;

import clinica.model.Animal;

/**
 * Cria a cadeia de cálculo correta para um atendimento,
 * aplicando automaticamente o desconto de animal adotado quando pertinente.
 *
 * Padrão: Factory Method
 * — centraliza a lógica de criação, evitando que o chamador
 *   precise conhecer quais decoradores montar.
 */
public class FabricaServico {

    private FabricaServico() {
        // Classe utilitária — não instanciável
    }

    /**
     * Cria um cálculo simples (apenas valor base), sem adicionais.
     */
    public static CalculoServico criarSimples(ServicoVeterinario servico) {
        return new ServicoBase(servico);
    }

    /**
     * Cria um cálculo base e aplica desconto de adoção automaticamente
     * se o animal for adotado.
     */
    public static CalculoServico criarComDesconto(ServicoVeterinario servico, Animal animal) {
        CalculoServico calculo = new ServicoBase(servico);
        if (animal.isAdotado()) {
            calculo = new DescontoAnimalAdotado(calculo);
        }
        return calculo;
    }

    /**
     * Cria um cálculo completo com atendimento domiciliar e banho pós-consulta,
     * aplicando desconto de adoção se cabível.
     */
    public static CalculoServico criarDomiciliarComBanho(ServicoVeterinario servico, Animal animal) {
        CalculoServico calculo = criarComDesconto(servico, animal);
        calculo = new TaxaAtendimentoDomiciliar(calculo);
        calculo = new BanhoPosConsulta(calculo);
        return calculo;
    }
}
