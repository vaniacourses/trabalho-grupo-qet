import backend.Agenda;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AgendaIntegrationTest {

    @Test
    void testAdicionarPessoaFisicaNaAgenda() {
        Endereco endereco = new Endereco(
            "Rua das Flores", "100", "Ap 10", "Centro",
            "São Paulo", "SP", "Brasil", "01000-000"
        );

        PessoaFisica pessoa = new PessoaFisica(
            "João", "1234", "joao@email.com", "123.456.789-00",
            "senha123", endereco
        );

        Agenda agenda = new Agenda();
        agenda.adicionarContato(pessoa);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("João", agenda.getContatos().get(0).getNome());
        assertEquals("joao@email.com", agenda.toString());
    }
}
