import backend.Agenda;
import backend.farmacia.PessoaJuridica;
import backend.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AgendaFarmaciaIT {

    @Test
    void testAdicionarFarmaciaNaAgenda() {
        Endereco endereco = new Endereco(
            "Av. Paulista", "200", "Sala 300", "Bela Vista",
            "São Paulo", "SP", "Brasil", "01310-000"
        );

        PessoaJuridica farmacia = new PessoaJuridica(
            "Farmacia Central", "1111-1111", "farmacia@email.com",
            "12.345.678/0001-99", "farmacia123", endereco
        );

        Agenda agenda = new Agenda();
        agenda.adicionarContato(farmacia);

        assertEquals(1, agenda.getContatos().size());
        assertEquals("Farmacia Central", agenda.getContatos().get(0).getNome());
    }
}
