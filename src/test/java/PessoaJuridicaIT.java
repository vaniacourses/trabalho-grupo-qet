import backend.farmacia.PessoaJuridica;
import backend.usuario.PessoaFisica;
import backend.farmacia.Estoque;
import backend.farmacia.ItemEstoque;
import backend.Medicamento;
import backend.Agenda;
import backend.Endereco;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import base.BaseCRUDTest;
import base.FixedFarmacia;
import utils.BuilderUtils;

class PessoaJuridicaIT extends BaseCRUDTest {

    @Test
    void testSetEndereco() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        Endereco novoEndereco = new Endereco("Rua Nova", "123", "Bairro Novo", "Cidade Nova", "Estado Novo", "12345-678", null, null);
        farmacia.setEndereco(novoEndereco, true);
        assertEquals(novoEndereco.toString(), farmacia.getEndereco().toString());
    }

    @Test
    void testSetCnpj() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        String novoCnpj = "12.345.678/0001-90";
        farmacia.setCnpj(novoCnpj, true);
        assertEquals(novoCnpj, farmacia.getCnpj());
    }

    @Test
    void testToStringVazio() {
        PessoaJuridica farmacia = new PessoaJuridica("a", "b", "c", "d", null, null);
        String resultado = farmacia.toString();
        assertEquals(farmacia.getNome() + "," + farmacia.getTelefone() + "," + farmacia.getEmail() + "," + farmacia.getSenha() + "," + farmacia.getCnpj() + "," + farmacia.getEndereco() + "," + "null,null", resultado);
    }

    @Test
    void testToString() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        farmacia.addUsuarioAosContatos(pessoa);
        String resultado = farmacia.toString();
        assertEquals(farmacia.getNome() + "," + farmacia.getTelefone() + "," + farmacia.getEmail() + "," + farmacia.getSenha() + "," + farmacia.getCnpj() + "," + farmacia.getEndereco() + "," + "null," + farmacia.getContatosClientes().toString(), resultado);
        assertEquals("FarmaciaTeste,00000000,farmacia@email.com,senhaFarm,2100,Av Central/100/Loja 1/Centro/CidadeY/UF/Brasil/11111111,null,joao@email.com", resultado);
    }

    @Test
    void testParticularidade() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        Object endereco = farmacia.getParticularidade();
        assertEquals(farmacia.getEndereco(), endereco);

        farmacia.setParticularidade(new Endereco("Rua Teste", "456", "Bairro Teste", "Cidade Teste", "Estado Teste", "98765-432", null, null));
        assertEquals(farmacia.getEndereco(), farmacia.getParticularidade());
    }


    @Test
    void testSalvarERecuperarFarmaciaArquivo() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        PessoaJuridica recuperada = PessoaJuridica.resgatarFarmaciaArquivo(farmacia.getEmail(), farmacia.getSenha(), false, false);

        assertNotNull(recuperada);
        assertEquals(farmacia.getNome(), recuperada.getNome());
        assertEquals(farmacia.getCnpj(), recuperada.getCnpj());
        assertEquals(farmacia.getTelefone(), recuperada.getTelefone());
        assertEquals(farmacia.getEmail(), recuperada.getEmail());
        assertEquals(farmacia.getEndereco().toString(), recuperada.getEndereco().toString());
    }

    @Test
    void testAdicionarMedicamentoEstoqueEPersistir() {
       PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        Medicamento medicamento = BuilderUtils.criarMedicamento();
        farmacia.adicionarMedicamentoEstoque(medicamento, 20);

        // Persistir estoque
        farmacia.salvarDadosArquivo();

        // Recuperar do arquivo
        PessoaJuridica recuperada = FixedFarmacia.resgatarFarmaciaArquivo(farmacia, false, false);
        assertNotNull(recuperada);
        Estoque estoque = recuperada.getEstoque();
        assertNotNull(estoque);
        assertEquals(1, estoque.listaEstoque.size());
        ItemEstoque item = estoque.listaEstoque.get(0);
        assertEquals(medicamento.getNome(), item.getMedicamento().getNome());
        assertEquals(20, item.getQntMedicamento());
    }

    @Test
    void testAtualizarQuantidadeMedicamentoEstoque() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        assertTrue(farmacia.getEstoque() == null || farmacia.getEstoque().listaEstoque.isEmpty());
        farmacia.atualizarQntMedicamentoEstoque(new Medicamento("segredo"), 10);


        Medicamento medicamento = BuilderUtils.criarMedicamento();
        farmacia.adicionarMedicamentoEstoque(medicamento, 10);
        farmacia.atualizarQntMedicamentoEstoque(medicamento, 30);

        farmacia.salvarDadosArquivo();

        PessoaJuridica recuperada = FixedFarmacia.resgatarFarmaciaArquivo(farmacia, false, false);
        assertNotNull(recuperada);
        Estoque estoque = recuperada.getEstoque();
        assertNotNull(estoque);
        assertEquals(2, estoque.listaEstoque.size());
        assertEquals(30, estoque.listaEstoque.get(1).getQntMedicamento());
    }

    @Test
    void testRemoverMedicamentoEstoque() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        Medicamento medicamento = BuilderUtils.criarMedicamento();

        farmacia.adicionarMedicamentoEstoque(medicamento, 5);
        farmacia.retirarMedicamentoEstoque(medicamento.getNome());

        farmacia.salvarDadosArquivo();

        PessoaJuridica recuperada = FixedFarmacia.resgatarFarmaciaArquivo(farmacia, false, false);
        assertNotNull(recuperada);
        Estoque estoque = recuperada.getEstoque();
        assertTrue(estoque == null || estoque.listaEstoque.isEmpty());
        farmacia.retirarMedicamentoEstoque(medicamento.getNome());
        assertTrue(estoque == null || estoque.listaEstoque.isEmpty());
    }

    @Test
    void testAddUsuarioAgenda() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();
        PessoaFisica pessoa = BuilderUtils.criarPessoaFisica();
        PessoaFisica pessoa2 = new BuilderUtils.PessoaFisicaBuilder().nome("outro").build();

        Agenda agenda = new Agenda();
        agenda.adicionarContato(pessoa);
        farmacia.addUsuarioAosContatos(pessoa);
        assertEquals(agenda.getContatos(), farmacia.getContatosClientes().getContatos());

        farmacia.addUsuarioAosContatos(pessoa2);
        assertEquals(2, farmacia.getContatosClientes().getContatos().size());
    }

    @Test
    void testResgatarEstoqueArquivoErro() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        Estoque estoque = PessoaJuridica.resgatarEstoqueArquivo("arquivo_errado.txt");
        assertTrue(estoque == null || estoque.listaEstoque.isEmpty());
    }

    @Test
    void testResgatarFarmaciaArquivoErro() {
        PessoaJuridica farmacia = BuilderUtils.criarPessoaJuridica();

        PessoaJuridica recuperada = PessoaJuridica.resgatarFarmaciaArquivo(farmacia.getEmail(), farmacia.getSenha(), false, false);
        assertNull(recuperada.getEstoque());
        assertNull(recuperada.getContatosClientes());
    }
}