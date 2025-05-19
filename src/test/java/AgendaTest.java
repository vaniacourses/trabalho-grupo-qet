import backend.Agenda;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgendaTest {

    private Agenda agenda;
    private PessoaFisica contato;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();
        Endereco endereco = new Endereco("Rua A", "123", "Ap 2", "Centro", "São Paulo", "SP", "Brasil", "01000-000");
        contato = new PessoaFisica("João", "1234", "joao@email.com", "123.456.789-00", "senha123", endereco);
    }

    @Test
    void testAdicionarContatoValido() {
        agenda.adicionarContato(contato);
        assertEquals(1, agenda.getContatos().size());
        assertEquals("João", agenda.getContatos().get(0).getNome());
    }

    @Test
    void testAdicionarContatoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> agenda.adicionarContato(null));
        assertEquals("É necessário informar um contato válido", exception.getMessage());
    }

    @Test
    void testAlterarTelefoneExistente() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarTelContato("João", "9999");
        assertTrue(resultado);
        assertEquals("9999", contato.getTelefone());
    }

    @Test
    void testAlterarTelefoneInexistente() {
        boolean resultado = agenda.alterarTelContato("Carlos", "8888");
        assertFalse(resultado);
    }

     @Test
    void testAlterarEmail() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarEmailContato("João", "novo@email.com");
        assertTrue(resultado);
        assertEquals("novo@email.com", contato.getEmail());
    }

    @Test
    void testAlterarParticularidade() {
        agenda.adicionarContato(contato);
        Endereco novoEndereco = new Endereco("Rua Nova", "456");
        boolean resultado = agenda.alterarParticularidadeContato("João", novoEndereco);
        assertTrue(resultado);
        assertEquals("Rua Nova", ((Endereco) contato.getParticularidade()).getNomeDaRua());
    }

    @Test
    void testRemoverContatoExistente() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.removerContato("João");
        assertTrue(resultado);
        assertEquals(0, agenda.getContatos().size());
    }

    @Test
    void testRemoverContatoInexistente() {
        boolean resultado = agenda.removerContato("Carlos");
        assertFalse(resultado);
    }

    @Test
    void testToStringComContato() {
        agenda.adicionarContato(contato);
        assertEquals("joao@email.com", agenda.toString());
    }

    @Test
    void testToStringVazio() {
        assertEquals("null", agenda.toString());
    }
}