import backend.Agenda;
import backend.Endereco;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;
import utils.AmbienteTemporario;
import utils.BuilderUtils;
import backend.FuncoesArquivos;
import backend.Pessoa;
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
    @Test
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

        Agenda novaAgenda = new Agenda();
        novaAgenda.adicionarContato(pessoa);

        boolean resultado = novaAgenda.alterarTelContato("João", "9999-9999");

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

    @Test
    void testAlterarContato() {
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        agenda.adicionarContato(pessoa);
        PessoaFisica pessoa2 = new BuilderUtils.PessoaFisicaBuilder().nome("pedro").build();
        agenda.adicionarContato(pessoa2);
        PessoaFisica pessoa3 = new BuilderUtils.PessoaFisicaBuilder().nome("maria").build();
        agenda.adicionarContato(pessoa3);

        agenda.alterarNomeContato(pessoa3.getNome(), "teste");

        assertNotNull(agenda.getContatos());
        assertEquals(3, agenda.getContatos().size());
        assertEquals(agenda.getContatos().get(2).getNome(), "teste");

        agenda.alterarEmailContato(pessoa.getNome(), "megateste");
        assertEquals("megateste", agenda.getContatos().get(0).getEmail());
        agenda.alterarParticularidadeContato(pessoa2.getNome(), new Endereco("Rua Nova", "200", "Casa 1", "Bairro", "Cidade", "UF", "Brasil", "02000-000"));
        assertEquals("Rua Nova/200/Casa 1/Bairro/Cidade/UF/Brasil/02000-000", agenda.getContatos().get(1).getParticularidade().toString());
        agenda.alterarTelContato(pessoa2.getNome(), "99999999");
        assertEquals("99999999", agenda.getContatos().get(1).getTelefone());
    }

    @Test
    void testAlterarContatoInvalido() {
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();

        assertThrows(IllegalArgumentException.class, () -> {
            agenda.alterarNomeContato(null, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            agenda.alterarNomeContato(null, "teste");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            agenda.alterarNomeContato(pessoa.getNome(), null);
        });

        agenda.adicionarContato(pessoa);
        assertFalse(agenda.alterarNomeContato("nsei", "nah"));
    }

    @Test
    void testRemoverContrato(){
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        agenda.adicionarContato(pessoa);

        boolean resultado = agenda.removerContato("João");
        assertTrue(resultado);
        assertEquals(0, agenda.getContatos().size());

        // Verifica se o contato foi removido
        assertFalse(agenda.getContatos().contains(pessoa));
    }

    @Test
    void testRemoverContatoInvalido() {
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        agenda.adicionarContato(pessoa);

        boolean resultado = agenda.removerContato("Inexistente");
        assertFalse(resultado);
        assertEquals(1, agenda.getContatos().size());

        assertThrows(IllegalArgumentException.class, () -> {
            agenda.removerContato(null);
        });
    }

@Test
void testGetContatosOrdenados() {
    // Adiciona contatos fora de ordem alfabética
    PessoaFisica pessoa1 = new BuilderUtils.PessoaFisicaBuilder().nome("Beto").build();
    PessoaFisica pessoa2 = new BuilderUtils.PessoaFisicaBuilder().nome("Ana").build();
    PessoaFisica pessoa3 = new BuilderUtils.PessoaFisicaBuilder().nome("Carla").build();

    agenda.adicionarContato(pessoa1);
    agenda.adicionarContato(pessoa2);
    agenda.adicionarContato(pessoa3);

    // Obtém a lista e verifica se está ordenada
    List<Pessoa> contatos = agenda.getContatos();
    
    assertEquals(3, contatos.size());
    assertEquals(pessoa2.getNome(), contatos.get(0).getNome());
    assertEquals(pessoa1.getNome(), contatos.get(1).getNome());
    assertEquals(pessoa3.getNome(), contatos.get(2).getNome());
}
}
