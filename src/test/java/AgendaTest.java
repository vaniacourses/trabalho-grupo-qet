import backend.Agenda;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgendaTest {

    private Agenda agenda;
    private PessoaFisica contato;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();
        Endereco endereco = new Endereco(
            "Rua A", "123", "Ap 2", "Centro", "São Paulo", "SP", "Brasil", "01000-000"
        );
        contato = new PessoaFisica(
            "João", "1234", "joao@email.com", "123.456.789-00", "senha123", endereco
        );
    }

    @Test
    void testAdicionarContatoValido() {
        agenda.adicionarContato(contato);
        assertEquals(1, agenda.getContatos().size());
        assertEquals("João", agenda.getContatos().get(0).getNome());
    }

    @Test
    void testAdicionarContatoNulo() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> agenda.adicionarContato(null)
        );
        assertEquals("É necessário informar um contato válido", exception.getMessage());
    }

    @Test
    void testAdicionarDoisContatos() {
        PessoaFisica pessoa1 = mock(PessoaFisica.class);
        when(pessoa1.getNome()).thenReturn("Maria");

        PessoaFisica pessoa2 = mock(PessoaFisica.class);
        when(pessoa2.getNome()).thenReturn("João");

        agenda.adicionarContato(pessoa1);
        agenda.adicionarContato(pessoa2);

        assertEquals(2, agenda.getContatos().size());
    }

    @Test
    void testAdicionarContatoDuplicado() {
        agenda.adicionarContato(contato);
        agenda.adicionarContato(contato);
        assertEquals(2, agenda.getContatos().size());
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
    void testAlterarTelefoneParaMesmoValor() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarTelContato("João", "1234");
        assertTrue(resultado);
        assertEquals("1234", contato.getTelefone());
    }

    @Test
    void testAlterarTelefoneCaseSensitivity() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarTelContato("joão", "7777");
        assertFalse(resultado);
    }

    @Test
    void testAlterarTelefoneParaNull() {
        agenda.adicionarContato(contato);
        assertThrows(IllegalArgumentException.class,
            () -> agenda.alterarTelContato("João", null));
    }

    @Test
    void testAlterarTelefoneParaVazio() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarTelContato("João", "");
        assertTrue(resultado);
        assertEquals("", contato.getTelefone());
    }

    @Test
    void testAlterarEmail() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.alterarEmailContato("João", "novo@email.com");
        assertTrue(resultado);
        assertEquals("novo@email.com", contato.getEmail());
    }

    @Test
    void testAlterarEmailContatoNaoExistente() {
        boolean resultado = agenda.alterarEmailContato("Carlos", "carlos@email.com");
        assertFalse(resultado);
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
    void testAlterarParticularidadeContatoInexistente() {
        boolean resultado = agenda.alterarParticularidadeContato("Carlos", new Endereco("Rua X", "10"));
        assertFalse(resultado);
    }

    @Test
    void testAlterarParticularidadeParaNull() {
        agenda.adicionarContato(contato);
        assertThrows(IllegalArgumentException.class,
            () -> agenda.alterarParticularidadeContato("João", null));
    }

    @Test
    void testRemoverContatoExistente() {
        agenda.adicionarContato(contato);
        boolean resultado = agenda.removerContato("João");
        assertTrue(resultado);
        assertEquals(0, agenda.getContatos().size());
    }

    @Test
    void testRemoverContatoNaoExistente() {
        boolean result = agenda.removerContato("Inexistente");
        assertFalse(result);
    }

    @Test
    void testRemoverContatoNomeNull() {
        assertThrows(IllegalArgumentException.class,
            () -> agenda.removerContato(null));
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

    @Test
    void testToStringComVariosContatos() {
        Endereco endereco2 = new Endereco("Rua B", "456");
        PessoaFisica contato2 = new PessoaFisica(
            "Maria", "5678", "maria@email.com", "234.567.890-11", "senha456", endereco2
        );
        agenda.adicionarContato(contato);
        agenda.adicionarContato(contato2);
        String result = agenda.toString();

        assertTrue(result.contains("joao@email.com"));
        assertTrue(result.contains("maria@email.com"));
    }

    @Test
    void testAlterarNomeContatoValido() {
        agenda.adicionarContato(contato);
        boolean result = agenda.alterarNomeContato("João", "Jose");
        assertTrue(result);
        assertEquals("Jose", agenda.getContatos().get(0).getNome());
    }

  
    @Test
    void testAlterarNomeContatoNull() {
        assertThrows(IllegalArgumentException.class,
            () -> agenda.alterarNomeContato(null, "NovoNome"));
    }

    @Test
    void testAlterarNomeParaNull() {
        agenda.adicionarContato(contato);
        assertThrows(IllegalArgumentException.class,
            () -> agenda.alterarNomeContato("João", null));
    }

        
    @Test
    void testAlterarNomeContatoNaoExistente() {
        boolean result = agenda.alterarNomeContato("Fulano", "NovoNome");
        assertFalse(result);
    }

   
   
    @Test
    void testAlterarEmailParaMesmoValor() {
        agenda.adicionarContato(contato);
        boolean result = agenda.alterarEmailContato("João", "joao@email.com");
        assertTrue(result);
        assertEquals("joao@email.com", contato.getEmail());
    }

    @Test
    void testAlterarEmailParaVazio() {
        agenda.adicionarContato(contato);
        boolean result = agenda.alterarEmailContato("João", "");
        assertTrue(result);
        assertEquals("", contato.getEmail());
    }

    @Test
    void testAlterarParticularidadeParaMesmoObjeto() {
        agenda.adicionarContato(contato);
        Endereco atual = (Endereco) contato.getParticularidade();
        boolean result = agenda.alterarParticularidadeContato("João", atual);
        assertTrue(result);
    }

    @Test
    void testRemoverContatoEmListaVazia() {
        boolean result = agenda.removerContato("João");
        assertFalse(result);
    }

    
    
    @Test
    void testToStringComContatoSemEmail() {
        Endereco endereco = new Endereco("Rua B", "456");
        PessoaFisica contatoSemEmail = new PessoaFisica(
            "Maria", "5678", null, "234.567.890-11", "senha456", endereco
        );
        agenda.adicionarContato(contatoSemEmail);
        String result = agenda.toString();
        assertTrue(result.contains("null") || result.isEmpty());
    }

}
