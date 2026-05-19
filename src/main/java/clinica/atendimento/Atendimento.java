package clinica.atendimento;

import clinica.model.Animal;
import clinica.model.Tutor;
import clinica.model.Veterinario;
import clinica.notificacao.ObservadorAtendimento;
import clinica.servico.ServicoVeterinario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contexto central do sistema.
 * Delega o controle de transições à situação atual (State).
 * Notifica observadores a cada mudança (Observer).
 */
public class Atendimento {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String id;
    private final Tutor tutor;
    private final Animal animal;
    private final Veterinario veterinario;
    private final ServicoVeterinario servico;
    private final LocalDateTime dataAgendamento;

    private SituacaoAtendimento situacao;
    private final List<ObservadorAtendimento> observadores = new ArrayList<>();

    public Atendimento(String id,
                       Tutor tutor,
                       Animal animal,
                       Veterinario veterinario,
                       ServicoVeterinario servico) {
        this.id = id;
        this.tutor = tutor;
        this.animal = animal;
        this.veterinario = veterinario;
        this.servico = servico;
        this.dataAgendamento = LocalDateTime.now();
        this.situacao = new SituacaoAgendado();
    }

    // ── Transições de situação ──────────────────────────────────────────────

    public void iniciar() {
        situacao.iniciar(this);
    }

    public void finalizar() {
        situacao.finalizar(this);
    }

    public void cancelar() {
        situacao.cancelar(this);
    }

    // ── Gerência de observers ───────────────────────────────────────────────

    public void adicionarObservador(ObservadorAtendimento observador) {
        observadores.add(observador);
    }

    public void removerObservador(ObservadorAtendimento observador) {
        observadores.remove(observador);
    }

    /** Chamado pelos estados concretos após cada transição. */
    public void notificarObservadores(String evento) {
        for (ObservadorAtendimento obs : observadores) {
            obs.atualizar(this, evento);
        }
    }

    // ── Acesso ao estado interno ────────────────────────────────────────────

    /** Usado pelos estados concretos para trocar a situação atual. */
    public void setSituacao(SituacaoAtendimento novaSituacao) {
        this.situacao = novaSituacao;
    }

    public SituacaoAtendimento getSituacao() {
        return situacao;
    }

    public String getSituacaoNome() {
        return situacao.getNome();
    }

    // ── Getters ─────────────────────────────────────────────────────────────

    public String getId() { return id; }

    public Tutor getTutor() { return tutor; }

    public Animal getAnimal() { return animal; }

    public Veterinario getVeterinario() { return veterinario; }

    public ServicoVeterinario getServico() { return servico; }

    public LocalDateTime getDataAgendamento() { return dataAgendamento; }

    public List<ObservadorAtendimento> getObservadores() {
        return Collections.unmodifiableList(observadores);
    }

    @Override
    public String toString() {
        return String.format(
            "[%s] Atendimento #%s | %s → %s | Serviço: %s | Situação: %s",
            dataAgendamento.format(FORMATTER),
            id,
            tutor.getNome(),
            animal.getNome(),
            servico.getDescricao(),
            situacao.getNome()
        );
    }
}
