import backend.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {

    @Test
    void testConstrutorBasico() {
        Endereco e = new Endereco("Rua A", "123");
        assertEquals("Rua A", e.getNomeDaRua());
        assertEquals("123", e.getNumero());
        assertNull(e.getComplemento());
    }

    @Test
    void testConstrutorComComplemento() {
        Endereco e = new Endereco("Rua B", "45", "Ap 2");
        assertEquals("Rua B", e.getNomeDaRua());
        assertEquals("45", e.getNumero());
        assertEquals("Ap 2", e.getComplemento());
    }

    @Test
    void testConstrutorComCep() {
        Endereco e = new Endereco("Rua C", "10", "Casa", "12345-678");
        assertEquals("12345-678", e.getCep());
    }

    @Test
    void testConstrutorCompleto() {
        Endereco e = new Endereco("Rua D", "100", "Ap 5", "Centro", "São Paulo", "SP", "Brasil", "00000-000");
        assertEquals("Centro", e.getNomeDoBairro());
        assertEquals("São Paulo", e.getNomeDaCidade());
        assertEquals("SP", e.getNomeDoEstado());
        assertEquals("Brasil", e.getNomeDoPais());
    }

    @Test
    void testToStringComValoresCompletos() {
        Endereco e = new Endereco("Rua E", "20", "Ap", "Bairro", "Cidade", "UF", "Brasil", "11111-111");
        String esperado = "Rua E/20/Ap/Bairro/Cidade/UF/Brasil/11111-111";
        assertEquals(esperado, e.toString());
    }

    @Test
    void testToStringComNulos() {
        Endereco e = new Endereco("Rua F", "30");
        String esperado = "Rua F/30/null/null/null/null/null/null";
        assertEquals(esperado, e.toString());
    }

    @Test
    void testToStringFrontCompletos() {
        Endereco e = new Endereco("Rua G", "77", "Ap", "Centro", "Cidade", "UF", "Brasil", "22222-222");
        String esperado = "Rua G,77,Ap,Centro,Cidade,UF,Brasil,22222-222";
        assertEquals(esperado, e.toStringFront());
    }

    @Test
    void testToStringFrontComNulos() {
        Endereco e = new Endereco("Rua H", "88");
        String esperado = "Rua H,88";
        assertEquals(esperado, e.toStringFront());
    }

    @Test
    void testStringToEndereco() {
        String enderecoString = "Rua I/99/Ap 1/Centro/Cidade/UF/Brasil/33333-333";
        Endereco e = Endereco.stringToEndereco(enderecoString);
        assertEquals("Rua I", e.getNomeDaRua());
        assertEquals("99", e.getNumero());
        assertEquals("Ap 1", e.getComplemento());
        assertEquals("Centro", e.getNomeDoBairro());
        assertEquals("Cidade", e.getNomeDaCidade());
        assertEquals("UF", e.getNomeDoEstado());
        assertEquals("Brasil", e.getNomeDoPais());
        assertEquals("33333-333", e.getCep());
    }
}
