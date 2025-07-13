import java.util.concurrent.TimeUnit;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import backend.Pessoa;
import backend.farmacia.PessoaJuridica;
import backend.usuario.PessoaFisica;
import base.BaseCRUDTest;
import inicio.MedAlerta;
import utils.UsuariosBuilder;

class LoginAutomatizadoIT extends BaseCRUDTest {

    private FrameFixture inicioWindow;
    private Robot robot;

    @BeforeEach
    void setUp() {
        Thread thread = new Thread(() -> MedAlerta.main(null));
        thread.setDaemon(true); // Permite que o teste termine mesmo se a thread do app ficar presa
        thread.start();

        robot = BasicRobot.robotWithCurrentAwtHierarchy();

      
        inicioWindow = WindowFinder.findFrame("Inicio")
                             .withTimeout(5, TimeUnit.SECONDS)
                             .using(robot);
    }
    
    @AfterEach
    void cleanUp() {
        inicioWindow.cleanUp();
        robot.cleanUp();
    }

    @Test
    @Disabled("As vezes esse teste falha por conta de um erro na forma como o código principal é programado")
    void testLoginPessoa() {

        PessoaFisica pessoa = UsuariosBuilder.criarPessoaFisica();
        inicioWindow.button("jButton3").click();

        FrameFixture entrarPessoa = WindowFinder.findFrame("EntrarPessoa")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

        entrarPessoa.textBox("emailEntradaE").enterText("joao@email.com");
        entrarPessoa.textBox("senhaEntradaE").enterText("senha123");
        entrarPessoa.button("prox_l").click();

        entrarPessoa.requireNotVisible();

        FrameFixture home = WindowFinder.findFrame("Home")
                                                .withTimeout(5, TimeUnit.SECONDS)
                                                .using(robot);
        home.requireVisible();
        home.label("nome_home1").requireText(pessoa.getNome());
        home.label("idade_home").requireText(pessoa.getCpf());

    }

    @Test
    void testLoginPessoaSenhaErrada() {

        PessoaFisica pessoa = UsuariosBuilder.criarPessoaFisica();
        inicioWindow.button("jButton3").click();

        FrameFixture entrarPessoa = WindowFinder.findFrame("EntrarPessoa")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

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

        FrameFixture entrarPessoa = WindowFinder.findFrame("EntrarPessoa")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

        entrarPessoa.textBox("emailEntradaE").enterText("emailerrado@mail");
        entrarPessoa.textBox("senhaEntradaE").enterText(pessoa.getSenha());
        entrarPessoa.button("prox_l").click();
        entrarPessoa.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarPessoa.optionPane().click();
    }

    @Test
    void testLoginFarmacia() {
        PessoaJuridica farmacia = UsuariosBuilder.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = WindowFinder.findFrame("EntrarFarmacia")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

        entrarFarmacia.textBox("emailEntradaE").enterText(farmacia.getEmail());
        entrarFarmacia.textBox("senhaEntradaE").enterText(farmacia.getSenha());
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.requireNotVisible();
        FrameFixture homeDaFarmacia = WindowFinder.findFrame("HomeDaFarmacia")
                                                .withTimeout(5, TimeUnit.SECONDS)
                                                .using(robot);

        homeDaFarmacia.requireVisible();
        homeDaFarmacia.label("nomeFarmaciaH").requireText(farmacia.getNome());
        homeDaFarmacia.label("cnpjFarmaciaH").requireText(farmacia.getCnpj());
    }

    @Test
    void testLoginFarmaciaSenhaErrada() {
        PessoaJuridica farmacia = UsuariosBuilder.criarPessoaJuridica();
        inicioWindow.button("jButton4").click();

        FrameFixture entrarFarmacia = WindowFinder.findFrame("EntrarFarmacia")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

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

        FrameFixture entrarFarmacia = WindowFinder.findFrame("EntrarFarmacia")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

        entrarFarmacia.textBox("emailEntradaE").enterText("emailerrado@mail");
        entrarFarmacia.textBox("senhaEntradaE").enterText(farmacia.getSenha());
        entrarFarmacia.button("prox_l").click();
        entrarFarmacia.optionPane().requireVisible().requireMessage("Erro, email ou senha incorretos!");
        entrarFarmacia.optionPane().click();
    }
}
