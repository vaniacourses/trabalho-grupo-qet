package utils;


import backend.Endereco;
import backend.Medicamento;
import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class BuilderUtils {

    public static PessoaFisica criarPessoaFisica() {
        Endereco enderecoPessoa = new Endereco(
            "Rua das Flores", "100", "Ap 10", "Centro",
            "São Paulo", "SP", "Brasil", "01000-000"
        );
        PessoaFisica pessoa = new PessoaFisica(
            "João", "1234", "joao@email.com", "123.456.789-00",
            "senha123", enderecoPessoa
        );

        pessoa.salvarDadosArquivo();
        return pessoa;
    }

    public static PessoaJuridica criarPessoaJuridica(){
        Endereco enderecoFarm = new Endereco(
            "Av Central", "100", "Loja 1", "Centro",
            "CidadeY", "UF", "Brasil", "11111111"
        );
        PessoaJuridica farmacia = new PessoaJuridica(
            "FarmaciaTeste", "00000000", "farmacia@email.com", "senhaFarm", "2100", enderecoFarm
        );
        farmacia.salvarDadosArquivo();
        return farmacia;
    }

    public static Medico criarMedico() {
        Medico medico = new Medico(
            "MedicoTeste", "77777777", "medico@email.com", "senhaMed", "Cardiologia"
        );
        medico.salvarDadosArquivo();
        return medico;
    }

    public static Medicamento criarMedicamento() {
        return new Medicamento("Dipirona", 10.0f, "500mg", "Comprimido", "Seco", false);
    }
}
