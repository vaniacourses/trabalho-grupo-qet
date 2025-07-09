import backend.Agenda;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import backend.farmacia.PessoaJuridica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class AgendaIntegrationTest {

    @TempDir
    Path tempDir;

    String originalArquivoUsuarios;
    String originalArquivoFarmacias;
    String originalArquivoMedicos;

    Agenda agenda;
    PessoaFisica pessoa;
    PessoaJuridica farmacia;
    Medico medico;

    @BeforeEach
    void setup() throws IOException {
        originalArquivoUsuarios = PessoaFisica.nomeArquivoUsuarios;
        originalArquivoFarmacias = PessoaJuridica.nomeArquivoFarmacias;
        originalArquivoMedicos = Medico.nomeArquivoMedicos;

        String tempUsuarios = tempDir.resolve("usuarios.txt").toString();
        String tempFarmacias = tempDir.resolve("farmacias.txt").toString();
        String tempMedicos = tempDir.resolve("medicos.txt").toString();

        PessoaFisica.nomeArquivoUsuarios = tempUsuarios;
        PessoaJuridica.nomeArquivoFarmacias = tempFarmacias;
        Medico.nomeArquivoMedicos = tempMedicos;

        Endereco enderecoPessoa = new Endereco(
            "Rua das Flores", "100", "Ap 10", "Centro",
            "São Paulo", "SP", "Brasil", "01000-000"
        );
        pessoa = new PessoaFisica(
            "João", "1234", "joao@email.com", "123.456.789-00",
            "senha123", enderecoPessoa
        );
        pessoa.salvarDadosArquivo();

        Endereco enderecoFarm = new Endereco(
            "Av Central", "100", "Loja 1", "Centro",
            "CidadeY", "UF", "Brasil", "11111-111"
        );
        farmacia = new PessoaJuridica(
            "FarmaciaTeste", "8888-8888", "farmacia@email.com", "senhaFarm", "12.345.678/0001-99", enderecoFarm
        );
        farmacia.salvarDadosArquivo();

        medico = new Medico(
            "MedicoTeste", "7777-7777", "medico@email.com", "senhaMed", "Cardiologia"
        );
        medico.salvarDadosArquivo();

        agenda = new Agenda();
    }

    @AfterEach
    void cleanup() {
        // Restaura os paths originais
        PessoaFisica.nomeArquivoUsuarios = originalArquivoUsuarios;
        PessoaJuridica.nomeArquivoFarmacias = originalArquivoFarmacias;
        Medico.nomeArquivoMedicos = originalArquivoMedicos;
    }

    @Test
    void testStringToAgendaPessoa() {
        Agenda agendaPessoa = Agenda.stringToAgenda("joao@email.com", "", "usuario", true, true);
        assertEquals(1, agendaPessoa.getContatos().size());
        assertEquals("joao@email.com", agendaPessoa.getContatos().get(0).getEmail());
        // // Testa farmácia
        // Agenda agendaFarm = Agenda.stringToAgenda("farmacia@email.com", "senhaFarm", "farmacia", true, true);
        // assertEquals(1, agendaFarm.getContatos().size());
        // assertEquals("farmacia@email.com", agendaFarm.getContatos().get(0).getEmail());
        // // Testa médico
        // Agenda agendaMed = Agenda.stringToAgenda("medico@email.com", "senhaMed", "medico", true, true);
        // assertEquals(1, agendaMed.getContatos().size());
        // assertEquals("medico@email.com", agendaMed.getContatos().get(0).getEmail());
    }

    @Test
    void testStringToAgendaFarmacia() {
        Agenda agendaFarmacia = Agenda.stringToAgenda("farmacia@email.com", "senhaFarm", "farmacia", true, true);
        assertEquals(1, agendaFarmacia.getContatos().size());
        assertEquals("farmacia@email.com", agendaFarmacia.getContatos().get(0).getEmail());
    }

    void testStringToAgendaMedico() {
        Agenda agendaMedico = Agenda.stringToAgenda("medico@email.com", "senhaMed", "medico", true, true);
        assertEquals(1, agendaMedico.getContatos().size());
        assertEquals("medico@email.com", agendaMedico.getContatos().get(0).getEmail());
    }

    @Test
    void testAdicionarPessoaFisicaNaAgenda() {
        agenda.adicionarContato(pessoa);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("João", agenda.getContatos().get(0).getNome());
        assertEquals("joao@email.com", agenda.toString());
    }
    
    @Test
    void testAdicionarFarmaciaNaAgenda() {
        agenda.adicionarContato(farmacia);
        assertEquals(1, agenda.getContatos().size());
        assertEquals("FarmaciaTeste", agenda.getContatos().get(0).getNome());
        assertEquals("farmacia@email.com", agenda.getContatos().get(0).getEmail());
    }

    @Test
    void testAdicionarMedicoNaAgenda() {
        agenda.adicionarContato(medico);
        assertEquals(1, agenda.getContatos().size());
        assertEquals("MedicoTeste", agenda.getContatos().get(0).getNome());
        assertEquals("medico@email.com", agenda.getContatos().get(0).getEmail());
    }
}
