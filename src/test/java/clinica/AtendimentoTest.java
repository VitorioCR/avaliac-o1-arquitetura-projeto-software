package clinica;

import clinica.atendimento.Atendimento;
import clinica.model.Animal;
import clinica.model.Tutor;
import clinica.model.Veterinario;
import clinica.notificacao.AvisoRecepcao;
import clinica.notificacao.AvisoTutor;
import clinica.notificacao.AvisoVeterinario;
import clinica.notificacao.CanalLog;
import clinica.registro.RegistroAtendimentos;
import clinica.servico.BanhoPosConsulta;
import clinica.servico.CalculoServico;
import clinica.servico.DescontoAnimalAdotado;
import clinica.servico.FabricaServico;
import clinica.servico.ServicoBase;
import clinica.servico.ServicoVeterinario;
import clinica.servico.TaxaAtendimentoDomiciliar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoTest {

    // ── Objetos compartilhados ──────────────────────────────────────────────

    private Tutor tutor;
    private Animal animalComum;
    private Animal animalAdotado;
    private Veterinario vet;

    @BeforeEach
    void setUp() {
        tutor         = new Tutor("Maria Silva", "11999990000", "maria@email.com");
        animalComum   = new Animal("Rex", "Cão", "Labrador", 3, false);
        animalAdotado = new Animal("Bolinha", "Cão", "SRD", 2, true);
        vet           = new Veterinario("Carlos Souza", "CRMV-12345", "Clínica Geral");

        RegistroAtendimentos.resetarInstancia();
    }

    // ── 1. Mudança VÁLIDA de situação ───────────────────────────────────────

    @Test
    @DisplayName("Deve transitar de Agendado para EmAtendimento ao iniciar")
    void deveIniciarAtendimentoAgendado() {
        Atendimento a = new Atendimento("001", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);

        assertEquals("Agendado", a.getSituacaoNome());

        a.iniciar();

        assertEquals("EmAtendimento", a.getSituacaoNome());
    }

    @Test
    @DisplayName("Deve transitar de EmAtendimento para Finalizado ao finalizar")
    void deveFinalizarAtendimentoEmAndamento() {
        Atendimento a = new Atendimento("002", tutor, animalComum, vet, ServicoVeterinario.VACINACAO);

        a.iniciar();
        assertEquals("EmAtendimento", a.getSituacaoNome());

        a.finalizar();

        assertEquals("Finalizado", a.getSituacaoNome());
    }

    @Test
    @DisplayName("Deve transitar de Agendado para Cancelado ao cancelar")
    void deveCancelarAtendimentoAgendado() {
        Atendimento a = new Atendimento("003", tutor, animalComum, vet, ServicoVeterinario.EXAME);

        a.cancelar();

        assertEquals("Cancelado", a.getSituacaoNome());
    }

    // ── 2. Tentativa de mudança INVÁLIDA ────────────────────────────────────

    @Test
    @DisplayName("Não deve permitir cancelar um atendimento já Finalizado")
    void naoDeveCancelarAtendimentoFinalizado() {
        Atendimento a = new Atendimento("004", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);

        a.iniciar();
        a.finalizar();

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            a::cancelar
        );
        assertTrue(ex.getMessage().contains("Finalizado"));
    }

    @Test
    @DisplayName("Não deve permitir iniciar um atendimento já Em Atendimento")
    void naoDeveIniciarAtendimentoJaIniciado() {
        Atendimento a = new Atendimento("005", tutor, animalComum, vet, ServicoVeterinario.CIRURGIA);

        a.iniciar();

        assertThrows(IllegalStateException.class, a::iniciar);
    }

    @Test
    @DisplayName("Não deve permitir finalizar direto de Agendado")
    void naoDeveFinalizarDeAgendadoDiretamente() {
        Atendimento a = new Atendimento("006", tutor, animalComum, vet, ServicoVeterinario.RETORNO);

        assertThrows(IllegalStateException.class, a::finalizar);
    }

    @Test
    @DisplayName("Não deve permitir nenhuma transição após Cancelado")
    void naoDeveTransitarDepoisDeCancelado() {
        Atendimento a = new Atendimento("007", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);

        a.cancelar();

        assertThrows(IllegalStateException.class, a::iniciar);
        assertThrows(IllegalStateException.class, a::finalizar);
        assertThrows(IllegalStateException.class, a::cancelar);
    }

    // ── 3. Envio automático de aviso (Observer + Bridge) ────────────────────

    @Test
    @DisplayName("Tutor deve ser avisado quando o atendimento for iniciado")
    void devAvisarTutorAoIniciar() {
        Atendimento a = new Atendimento("008", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);
        a.adicionarObservador(new AvisoTutor(new CanalLog()));

        ByteArrayOutputStream saida = capturarSaida();
        a.iniciar();
        restaurarSaida(saida);

        assertTrue(saida.toString().contains("Maria Silva"));
        assertTrue(saida.toString().contains("Rex"));
    }

    @Test
    @DisplayName("Veterinário deve ser avisado quando o atendimento for cancelado")
    void deveAvisarVeterinarioAoCancelar() {
        Atendimento a = new Atendimento("009", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);
        a.adicionarObservador(new AvisoVeterinario(new CanalLog()));

        ByteArrayOutputStream saida = capturarSaida();
        a.cancelar();
        restaurarSaida(saida);

        assertTrue(saida.toString().contains("Carlos Souza"));
        assertTrue(saida.toString().contains("cancelado"));
    }

    @Test
    @DisplayName("Recepção deve ser avisada quando o atendimento for finalizado")
    void deveAvisarRecepcaoAoFinalizar() {
        Atendimento a = new Atendimento("010", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);
        a.adicionarObservador(new AvisoRecepcao(new CanalLog()));

        ByteArrayOutputStream saida = capturarSaida();
        a.iniciar();
        a.finalizar();
        restaurarSaida(saida);

        assertTrue(saida.toString().contains("Recepção"));
        assertTrue(saida.toString().contains("nota fiscal"));
    }

    @Test
    @DisplayName("Observador não pertinente ao evento não deve gerar aviso")
    void naoDeveAvisarTutorAoCancelar() {
        Atendimento a = new Atendimento("011", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);
        a.adicionarObservador(new AvisoTutor(new CanalLog())); // só reage a INICIADO

        ByteArrayOutputStream saida = capturarSaida();
        a.cancelar();
        restaurarSaida(saida);

        // AvisoTutor não deve emitir nada para CANCELADO
        assertTrue(saida.toString().isBlank());
    }

    // ── 4. Cálculo do valor final com múltiplas regras (Decorator) ──────────

    @Test
    @DisplayName("Valor base de consulta deve ser R$ 120,00")
    void deveRetornarValorBaseConsulta() {
        CalculoServico calculo = new ServicoBase(ServicoVeterinario.CONSULTA);

        assertEquals(120.00, calculo.calcularValor(), 0.001);
    }

    @Test
    @DisplayName("Desconto de 15% para animal adotado sobre consulta R$ 120,00 → R$ 102,00")
    void deveAplicarDescontoAnimalAdotado() {
        CalculoServico calculo = new DescontoAnimalAdotado(
                                 new ServicoBase(ServicoVeterinario.CONSULTA));

        assertEquals(102.00, calculo.calcularValor(), 0.001);
    }

    @Test
    @DisplayName("Consulta + domiciliar + banho → R$ 200,00")
    void deveAplicarTaxaDomiciliarEBanho() {
        CalculoServico calculo = new BanhoPosConsulta(           // +30
                                 new TaxaAtendimentoDomiciliar(  // +50
                                 new ServicoBase(ServicoVeterinario.CONSULTA))); // 120

        assertEquals(200.00, calculo.calcularValor(), 0.001);
    }

    @Test
    @DisplayName("Consulta + adoção (15%) + domiciliar + banho → R$ 182,00")
    void deveAplicarTodasAsRegras() {
        // 120 - 18 (15%) = 102 + 50 + 30 = 182
        CalculoServico calculo = new BanhoPosConsulta(
                                 new TaxaAtendimentoDomiciliar(
                                 new DescontoAnimalAdotado(
                                 new ServicoBase(ServicoVeterinario.CONSULTA))));

        assertEquals(182.00, calculo.calcularValor(), 0.001);
    }

    @Test
    @DisplayName("Factory deve montar cadeia com desconto de adoção automaticamente")
    void deveCriarCadeiaComDescontoViaFactory() {
        CalculoServico calculo = FabricaServico.criarComDesconto(
                ServicoVeterinario.CONSULTA, animalAdotado);

        assertEquals(102.00, calculo.calcularValor(), 0.001);
    }

    @Test
    @DisplayName("Factory não deve aplicar desconto para animal não adotado")
    void naoDeveAplicarDescontoParaAnimalNaoAdotado() {
        CalculoServico calculo = FabricaServico.criarComDesconto(
                ServicoVeterinario.CONSULTA, animalComum);

        assertEquals(120.00, calculo.calcularValor(), 0.001);
    }

    // ── 5. Singleton do registro ─────────────────────────────────────────────

    @Test
    @DisplayName("RegistroAtendimentos deve retornar sempre a mesma instância")
    void deveSerSingleton() {
        RegistroAtendimentos r1 = RegistroAtendimentos.getInstancia();
        RegistroAtendimentos r2 = RegistroAtendimentos.getInstancia();

        assertSame(r1, r2);
    }

    @Test
    @DisplayName("Deve registrar e recuperar atendimentos por tutor")
    void deveRegistrarEBuscarPorTutor() {
        Atendimento a = new Atendimento("012", tutor, animalComum, vet, ServicoVeterinario.CONSULTA);

        RegistroAtendimentos registro = RegistroAtendimentos.getInstancia();
        registro.adicionar(a);

        assertEquals(1, registro.buscarPorTutor("Maria Silva").size());
        assertEquals(0, registro.buscarPorTutor("João").size());
    }

    // ── Auxiliares para captura de saída ─────────────────────────────────────

    private static final PrintStream SAIDA_ORIGINAL = System.out;

    private ByteArrayOutputStream capturarSaida() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        return baos;
    }

    private void restaurarSaida(ByteArrayOutputStream baos) {
        System.setOut(SAIDA_ORIGINAL);
    }
}
