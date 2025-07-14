import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

import backend.farmacia.PessoaJuridica;
import base.BaseAutomatizationTest;
import frontend.EntrarFarmacia;
import frontend.EstoqueFarmacia;
import frontend.HomeDaFarmacia;
import utils.BuilderUtils;

public class CadastroEstoqueFarmaciaAutomatizadoIT extends BaseAutomatizationTest {

    @Test
    void testCadastroEstoqueFarmacia() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
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

    @Test
    void testCadastroEstoqueFarmaciaComValoresInvalidos() throws InterruptedException {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
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
        cadastroEstoque.button("salvar").click();
        cadastroEstoque.requireVisible();
        cadastroEstoque.textBox("nomeRemedio").enterText("a");
        cadastroEstoque.textBox("quantidadeRemedio").enterText("a");
        cadastroEstoque.textBox("precoRemedio").enterText("a");
        cadastroEstoque.textBox("especificacoesRemedio").enterText("a");
        cadastroEstoque.button("salvar").click();
        cadastroEstoque.requireVisible();
        cadastroEstoque.table("tabelaRemedio").requireRowCount(0);
    }
    
}
