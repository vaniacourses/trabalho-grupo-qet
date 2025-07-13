import java.util.concurrent.TimeUnit;

import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

import backend.farmacia.PessoaJuridica;
import base.BaseAutomatizationTest;
import frontend.EntrarFarmacia;
import frontend.EstoqueFarmacia;
import frontend.HomeDaFarmacia;
import utils.UsuariosBuilder;

public class CadastroEstoqueFarmaciaIT extends BaseAutomatizationTest {

    @Test
    void testCadastroEstoqueFarmacia() throws InterruptedException {
        PessoaJuridica farmacia = UsuariosBuilder.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = findFrame(EntrarFarmacia.class);

        entrarFarmacia.textBox("emailEntradaE").enterText(farmacia.getEmail());
        entrarFarmacia.textBox("senhaEntradaE").enterText(farmacia.getSenha());
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.requireNotVisible();
        FrameFixture homeDaFarmacia = findFrame(HomeDaFarmacia.class);

        homeDaFarmacia.requireVisible();
        homeDaFarmacia.label("nomeFarmaciaH").requireText(farmacia.getNome());
        homeDaFarmacia.label("cnpjFarmaciaH").requireText(farmacia.getCnpj());

        homeDaFarmacia.button("jButton1").click();

        FrameFixture cadastroEstoque = findFrame(EstoqueFarmacia.class);

        cadastroEstoque.button("jButton1").click();
        cadastroEstoque.textBox("nomeRemedio").enterText("teste");
        cadastroEstoque.textBox("quantidadeRemedio").enterText("10");
        cadastroEstoque.textBox("precoRemedio").enterText("19.99");
        cadastroEstoque.textBox("especificacoesRemedio").enterText("nenhuma");
        cadastroEstoque.button("salvar").click();

        FrameFixture estoqueAtualizado = findFrame(EstoqueFarmacia.class);
        estoqueAtualizado.requireVisible();
        estoqueAtualizado.table("tabelaRemedio")
        .requireContents(new String[][] {
            {"teste", "10", "19.99", "nenhuma"}
        });
    }
    
}
