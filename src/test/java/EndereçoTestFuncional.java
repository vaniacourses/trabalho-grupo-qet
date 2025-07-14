package backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoFuncionalTest {

    @Test
    void deveCriarEnderecoCompletoCorretamente() {
        Endereco endereco = new Endereco("Av. Brasil", "100", "Bloco A", "Centro", "Rio", "RJ", "Brasil", "22222-000");

        assertEquals("Av. Brasil", endereco.getNomeDaRua());
        assertEquals("100", endereco.getNumero());
        assertEquals("Centro", endereco.getNomeDoBairro());
        assertEquals("22222-000", endereco.getCep());

        String esperado = "Av. Brasil,100,Bloco A,Centro,Rio,RJ,Brasil,22222-000";
        assertEquals(esperado, endereco.toStringFront());
    }
}