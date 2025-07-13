import backend.farmacia.PessoaJuridica;
import backend.farmacia.Estoque;
import backend.farmacia.ItemEstoque;
import backend.Medicamento;
import backend.Endereco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import base.BaseCRUDTest;
import base.FixedFarmacia;
import utils.BuilderUtils;

class PessoaJuridicaIT extends BaseCRUDTest {

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


        Medicamento medicamento = BuilderUtils.criarMedicamento();
        farmacia.adicionarMedicamentoEstoque(medicamento, 10);
        farmacia.atualizarQntMedicamentoEstoque(medicamento, 30);

        farmacia.salvarDadosArquivo();

        PessoaJuridica recuperada = FixedFarmacia.resgatarFarmaciaArquivo(farmacia, false, false);
        assertNotNull(recuperada);
        Estoque estoque = recuperada.getEstoque();
        assertNotNull(estoque);
        assertEquals(1, estoque.listaEstoque.size());
        assertEquals(30, estoque.listaEstoque.get(0).getQntMedicamento());
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
    }
}