import backend.Agenda;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;
import utils.AmbienteTemporario;
import utils.BuilderUtils;
import backend.FuncoesArquivos;
import backend.farmacia.PessoaJuridica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("integration")
class AgendaIT {

    @TempDir
    Path tempDir;

    Agenda agenda;

    @BeforeEach
    void setup() {

        AmbienteTemporario.iniciar(tempDir);

        agenda = new Agenda();
    }

    @AfterEach
    void cleanup() {
        AmbienteTemporario.terminar();
    }

    @Test
    void testStringToAgendaPessoa() {

        BuilderUtils.criarPessoaFisica();

        Agenda agendaPessoa = Agenda.stringToAgenda("joao@email.com", "", "usuario", true, true);
        assertEquals(1, agendaPessoa.getContatos().size());
        assertEquals("joao@email.com", agendaPessoa.getContatos().get(0).getEmail());
    }

    @Test
    void testStringToAgendaFarmacia() {

        BuilderUtils.criarPessoaJuridica();

        Agenda agendaFarmacia = Agenda.stringToAgenda("farmacia@email.com", "senhaFarm", "farmacia", true, true);
        assertEquals(1, agendaFarmacia.getContatos().size());
        assertEquals("farmacia@email.com", agendaFarmacia.getContatos().get(0).getEmail());
    }

    void testStringToAgendaMedico() {

        BuilderUtils.criarMedico();

        Agenda agendaMedico = Agenda.stringToAgenda("medico@email.com", "senhaMed", "medico", true, true);
        assertEquals(1, agendaMedico.getContatos().size());
        assertEquals("medico@email.com", agendaMedico.getContatos().get(0).getEmail());
    }

    @Test
    void testAdicionarPessoaFisicaNaAgenda() {

        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();

        agenda.adicionarContato(pessoa);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("João", agenda.getContatos().get(0).getNome());
        assertEquals("joao@email.com", agenda.toString());
    }
    
    @Test
    void testAdicionarFarmaciaNaAgenda() {

        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        agenda.adicionarContato(farmacia);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("FarmaciaTeste", agenda.getContatos().get(0).getNome());
        assertEquals("farmacia@email.com", agenda.getContatos().get(0).getEmail());
    }

    @Test
    void testAdicionarMedicoNaAgenda() {

        Medico medico = BuilderUtils.criarMedico();

        agenda.adicionarContato(medico);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("MedicoTeste", agenda.getContatos().get(0).getNome());
        assertEquals("medico@email.com", agenda.getContatos().get(0).getEmail());
    }

    @Test
    void testAlterarTelefonePessoaFisicaNaAgenda() {

        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();

        Agenda agenda = new Agenda();
        agenda.adicionarContato(pessoa);

        boolean resultado = agenda.alterarTelContato("João", "9999-9999");

        assertTrue(resultado);
        assertEquals("9999-9999", pessoa.getTelefone());
    }

    @Test
    void testRemoverContatoEPersistirAgenda() {

        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();

        agenda = new Agenda();
        agenda.adicionarContato(pessoa);

        // Remove contato
        boolean removido = agenda.removerContato("João");

        assertTrue(removido);
        assertEquals(0, agenda.getContatos().size());

        // Salva agenda no arquivo
        String dados = agenda.toString();
        String arquivo = "agendaRemocao.txt";

        FuncoesArquivos.escreverArquivo(arquivo, dados);

        List<String> linhas = FuncoesArquivos.obterListaLinhas(arquivo);
        String resultado = String.join("", linhas);

        assertEquals("null", resultado);
    }
}
