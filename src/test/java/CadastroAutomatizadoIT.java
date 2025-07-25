import java.util.concurrent.TimeUnit;

import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

import base.BaseAutomatizationTest;
class CadastroAutomatizadoIT extends BaseAutomatizationTest {

    @Test
    void testCadastroPessoa() {

        inicioWindow.button("jButton1").click();

        FrameFixture loginPessoa = WindowFinder.findFrame("LoginPessoa")
                                                  .withTimeout(5, TimeUnit.SECONDS)
                                                  .using(robot);

        loginPessoa.textBox("nome_l_e").enterText("teste");

        loginPessoa.textBox("idade_l_e").enterText("12345678901");
        
        loginPessoa.textBox("emailLPE").enterText("fulano@teste.com");
        
        loginPessoa.textBox("senhaLPE").enterText("senhaForte123");

        loginPessoa.textBox("telefoneL").enterText("11999998888");

        loginPessoa.textBox("ruaL").enterText("Rua dos Testes");
        loginPessoa.textBox("numeroL").enterText("123");
        loginPessoa.textBox("complementoL").enterText("Bloco A");
        loginPessoa.button("prox_l").click();
        loginPessoa.requireNotVisible();
        FrameFixture home = WindowFinder.findFrame("Home")
                                                .withTimeout(5, TimeUnit.SECONDS) 
                                                .using(robot);
        
        home.requireVisible();
        home.label("nome_home1").requireText("teste");
        home.label("idade_home").requireText("12345678901");
    }

    @Test
    void testCadastroFarmacia() {

        inicioWindow.button("jButton2").click();

        FrameFixture cadastroFarmacia = WindowFinder.findFrame("CadastroFarmacia")
            .withTimeout(5, TimeUnit.SECONDS)
            .using(robot);

        cadastroFarmacia.textBox("nomeFarmaciaEntrada").enterText("Farmacia Teste");
        cadastroFarmacia.textBox("cnpjFarmaciaEntrada").enterText("12345678000199");
        cadastroFarmacia.textBox("emailLFE").enterText("farmacia@teste.com");
        cadastroFarmacia.textBox("senhaLFE").enterText("senhaForte123");
        cadastroFarmacia.textBox("numeroDaFarmaciaEntrada").enterText("11999998888");
        cadastroFarmacia.textBox("ruaLF").enterText("Rua das Farmacias");
        cadastroFarmacia.textBox("numeroL").enterText("456");
        cadastroFarmacia.textBox("complementoLF").enterText("Sala 2");

        cadastroFarmacia.button("proximoFarmacia").click();
        cadastroFarmacia.requireNotVisible();

        FrameFixture homeDaFarmacia = WindowFinder.findFrame("HomeDaFarmacia")
            .withTimeout(5, TimeUnit.SECONDS)
            .using(robot);

        homeDaFarmacia.requireVisible();
        homeDaFarmacia.label("nomeFarmaciaH").requireText("Farmacia Teste");
        homeDaFarmacia.label("cnpjFarmaciaH").requireText("12345678000199");
    }
}
