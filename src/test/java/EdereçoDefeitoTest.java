package backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoDefeitosTest {

    @Test
    void deveTratarStringComNullLiteral() {
        String entrada = "Rua C/30/null/null/null/null/null/null";
        Endereco e = Endereco.stringToEndereco(entrada);

        assertNull(e.getComplemento());
        assertNull(e.getNomeDoBairro());
    }

    @Test
    void deveIgnorarCamposExtrasNaString() {
        String entrada = "Rua D/40/Ap/Centro/Cidade/Estado/Pais/12345-000/Extra";
        Endereco e = Endereco.stringToEndereco(entrada);

        assertEquals("Centro", e.getNomeDoBairro());
        assertEquals("12345-000", e.getCep());
    }

    @Test
    void deveLidarComCampoVazio() {
        String entrada = "Rua E/50///Cidade///12345-000";
        Endereco e = Endereco.stringToEndereco(entrada);

        assertNull(e.getComplemento());
        assertEquals("Cidade", e.getNomeDaCidade());
        assertEquals("12345-000", e.getCep());
    }
}