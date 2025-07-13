import java.util.concurrent.TimeUnit;

import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

import backend.farmacia.PessoaJuridica;
import backend.usuario.PessoaFisica;
import base.BaseAutomatizationTest;
import frontend.EntrarFarmacia;
import frontend.EntrarPessoa;
import utils.UsuariosBuilder;

class LoginInvalidoAutomatizadoIT extends BaseAutomatizationTest {

    @Test
    void testLoginPessoaSenhaErrada() {

        PessoaFisica pessoa = UsuariosBuilder.criarPessoaFisica();
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

        PessoaFisica pessoa = UsuariosBuilder.criarPessoaFisica();
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
        PessoaJuridica farmacia = UsuariosBuilder.criarPessoaJuridica();
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
        PessoaJuridica farmacia = UsuariosBuilder.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = findFrame(EntrarFarmacia.class);

        entrarFarmacia.textBox("emailEntradaE").enterText("emailerrado@mail");
        entrarFarmacia.textBox("senhaEntradaE").enterText(farmacia.getSenha());
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarFarmacia.optionPane().click();
    }
}
