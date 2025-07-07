import backend.Agenda;
import backend.usuario.PessoaFisica;
import backend.Endereco;
import backend.FuncoesArquivos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class RemocaoPersistenciaIntegrationTest {

    @Test
    void testRemoverContatoEPersistirAgenda() {
        Endereco endereco = new Endereco(
            "Rua C", "789", "", "Bairro",
            "Curitiba", "PR", "Brasil", "80000-000"
        );

        PessoaFisica pessoa = new PessoaFisica(
            "Carlos", "4321", "carlos@email.com",
            "222.333.444-55", "senha789", endereco
        );

        Agenda agenda = new Agenda();
        agenda.adicionarContato(pessoa);

        // Remove contato
        boolean removido = agenda.removerContato("Carlos");

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
