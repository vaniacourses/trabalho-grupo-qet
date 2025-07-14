package backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoEstruturalTest {

    @Test
    void deveExecutarTodosOsIfsDoToString() {
        Endereco endereco = new Endereco("Rua A", "10", "Casa", "Bairro", "Cidade", "Estado", "Pais", "11111-000");

        String esperado = "Rua A/10/Casa/Bairro/Cidade/Estado/Pais/11111-000";
        assertEquals(esperado, endereco.toString());
    }

    @Test
    void deveExecutarFluxoComCamposNulos() {
        Endereco endereco = new Endereco("Rua B", "20");

        String esperado = "Rua B/20/null/null/null/null/null/null";
        assertEquals(esperado, endereco.toString());
    }
}