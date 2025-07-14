package backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoIntegracaoTest {

    static class Usuario {
        private String nome;
        private Endereco endereco;

        public Usuario(String nome, Endereco endereco) {
            this.nome = nome;
            this.endereco = endereco;
        }

        public String getEnderecoFormatado() {
            return endereco.toStringFront();
        }
    }

    @Test
    void deveIntegrarEnderecoComUsuario() {
        Endereco endereco = new Endereco("Rua F", "99", "Bloco C", "Bairro", "Cidade", "UF", "Brasil", "99999-999");
        Usuario usuario = new Usuario("Ana", endereco);

        String esperado = "Rua F,99,Bloco C,Bairro,Cidade,UF,Brasil,99999-999";
        assertEquals(esperado, usuario.getEnderecoFormatado());
    }
}