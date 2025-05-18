package br.com.medalerta.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {

    @Test
    public void testConstrutorEndereco() {
        Endereco endereco = new Endereco("Rua das Flores", "123", "Centro", "Niterói", "RJ", "24360-000");
        assertEquals("Rua das Flores", endereco.getRua());
        assertEquals("123", endereco.getNumero());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Niterói", endereco.getCidade());
        assertEquals("RJ", endereco.getEstado());
        assertEquals("24360-000", endereco.getCep());
    }

    @Test
    public void testSettersEndereco() {
        Endereco endereco = new Endereco();
        endereco.setRua("Avenida Brasil");
        endereco.setNumero("456");
        endereco.setBairro("Icaraí");
        endereco.setCidade("Niterói");
        endereco.setEstado("RJ");
        endereco.setCep("24360-001");

        assertEquals("Avenida Brasil", endereco.getRua());
        assertEquals("456", endereco.getNumero());
        assertEquals("Icaraí", endereco.getBairro());
        assertEquals("Niterói", endereco.getCidade());
        assertEquals("RJ", endereco.getEstado());
        assertEquals("24360-001", endereco.getCep());
    }

    @Test
    public void testToStringEndereco() {
        Endereco endereco = new Endereco("Rua das Flores", "123", "Centro", "Niterói", "RJ", "24360-000");
        String esperado = "Endereco{rua='Rua das Flores', numero='123', bairro='Centro', cidade='Niterói', estado='RJ', cep='24360-000'}";
        assertEquals(esperado, endereco.toString());
    }
}