package clinica.registro;

import clinica.atendimento.Atendimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositório único de todos os atendimentos da clínica.
 * Garante que existe apenas uma instância em toda a aplicação.
 *
 * Padrão: Singleton (lazy + thread-safe via synchronized)
 */
public class RegistroAtendimentos {

    private static RegistroAtendimentos instancia;

    private final List<Atendimento> atendimentos = new ArrayList<>();

    private RegistroAtendimentos() {
        // construtor privado — use getInstancia()
    }

    public static synchronized RegistroAtendimentos getInstancia() {
        if (instancia == null) {
            instancia = new RegistroAtendimentos();
        }
        return instancia;
    }

    /** Adiciona um atendimento ao registro. */
    public void adicionar(Atendimento atendimento) {
        atendimentos.add(atendimento);
    }

    /** Retorna todos os atendimentos cadastrados (lista imutável). */
    public List<Atendimento> listar() {
        return Collections.unmodifiableList(atendimentos);
    }

    /** Busca todos os atendimentos de um tutor pelo nome (case-insensitive). */
    public List<Atendimento> buscarPorTutor(String nome) {
        return atendimentos.stream()
                .filter(a -> a.getTutor().getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }

    /** Busca todos os atendimentos por situação atual. */
    public List<Atendimento> buscarPorSituacao(String situacao) {
        return atendimentos.stream()
                .filter(a -> a.getSituacaoNome().equalsIgnoreCase(situacao))
                .collect(Collectors.toList());
    }

    /**
     * Limpa todos os atendimentos do registro.
     * Útil para resetar estado entre testes.
     */
    public void limpar() {
        atendimentos.clear();
    }

    /** Reinicia a instância singleton (uso exclusivo em testes). */
    public static synchronized void resetarInstancia() {
        instancia = null;
    }
}
