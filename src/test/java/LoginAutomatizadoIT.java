import java.util.concurrent.TimeUnit;

import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import backend.farmacia.PessoaJuridica;
import backend.usuario.PessoaFisica;
import base.BaseAutomatizationTest;
import frontend.EntrarFarmacia;
import frontend.EntrarPessoa;
import frontend.Home;
import frontend.HomeDaFarmacia;
import utils.BuilderUtils;

class LoginAutomatizadoIT extends BaseAutomatizationTest {

    @Test
    @Disabled("Algo na aplicação quebra o login quando ele é executado de maneira consequente com outros testes, por algum motivo ele abre duas janelas de si mesmo")
    void testLoginPessoa() {
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        inicioWindow.button("jButton3").click();

        FrameFixture entrarPessoa = findFrame(EntrarPessoa.class);

        entrarPessoa.textBox("emailEntradaE").enterText(pessoa.getEmail());
        entrarPessoa.textBox("senhaEntradaE").enterText(pessoa.getSenha());
        entrarPessoa.button("prox_l").click();
        entrarPessoa.requireNotVisible();

        FrameFixture home = findFrame(Home.class);

        home.requireVisible();
        home.label("nome_home1").requireText(pessoa.getNome());
        home.label("idade_home").requireText(pessoa.getCpf());
    }

    @Test
    void testLoginFarmacia(){
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
    }

    @Test
    void testLoginPessoaSenhaErrada() {

        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        inicioWindow.button("jButton3").click();

        FrameFixture entrarPessoa = findFrame(EntrarPessoa.class);

        entrarPessoa.textBox("emailEntradaE").enterText(pessoa.getEmail());
        entrarPessoa.textBox("senhaEntradaE").enterText("senhaErrada");
        entrarPessoa.button("prox_l").click();
        entrarPessoa.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarPessoa.optionPane().click();
    }

    @Test
    void testLoginPessoaEmailErrado() {

        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        inicioWindow.button("jButton3").click();

        FrameFixture entrarPessoa = findFrame(EntrarPessoa.class);

        entrarPessoa.textBox("emailEntradaE").enterText("emailerrado@mail");
        entrarPessoa.textBox("senhaEntradaE").enterText(pessoa.getSenha());
        entrarPessoa.button("prox_l").click();
        entrarPessoa.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarPessoa.optionPane().click();
    }

    @Test
    void testLoginFarmaciaSenhaErrada() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = findFrame(EntrarFarmacia.class);
        entrarFarmacia.textBox("emailEntradaE").enterText(farmacia.getEmail());
        entrarFarmacia.textBox("senhaEntradaE").enterText("senhaErrada");
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarFarmacia.optionPane().click();
    }

    @Test
    void testLoginFarmaciaEmailErrado() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = findFrame(EntrarFarmacia.class);

        entrarFarmacia.textBox("emailEntradaE").enterText("emailerrado@mail");
        entrarFarmacia.textBox("senhaEntradaE").enterText(farmacia.getSenha());
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarFarmacia.optionPane().click();
    }
}
