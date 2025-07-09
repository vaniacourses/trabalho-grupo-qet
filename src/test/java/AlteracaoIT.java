import backend.Agenda;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlteracaoIT {

    @Test
    void testAlterarTelefonePessoaFisicaNaAgenda() {
        Endereco endereco = new Endereco(
            "Rua B", "456", "Casa", "Centro",
            "Rio de Janeiro", "RJ", "Brasil", "20000-000"
        );

        PessoaFisica pessoa = new PessoaFisica(
            "Maria", "5678", "maria@email.com",
            "987.654.321-00", "senha456", endereco
        );

        Agenda agenda = new Agenda();
        agenda.adicionarContato(pessoa);

        boolean resultado = agenda.alterarTelContato("Maria", "9999-9999");

        assertTrue(resultado);
        assertEquals("9999-9999", pessoa.getTelefone());
    }
}
