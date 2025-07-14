package utils;


import backend.Endereco;
import backend.Medicamento;
import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class BuilderUtils {

    public static class PessoaFisicaBuilder {
    private String nome = "João";
    private String telefone = "1234";
    private String email = "joao@email.com";
    private String cpf = "123.456.789-00";
    private String senha = "senha123";
    private Endereco endereco = new Endereco("Rua das Flores", "100", "Ap 10", "Centro", "São Paulo", "SP", "Brasil", "01000-000");

    public PessoaFisicaBuilder nome(String nome) { this.nome = nome; return this; }
    public PessoaFisicaBuilder telefone(String telefone) { this.telefone = telefone; return this; }
    public PessoaFisicaBuilder email(String email) { this.email = email; return this; }
    public PessoaFisicaBuilder cpf(String cpf) { this.cpf = cpf; return this; }
    public PessoaFisicaBuilder senha(String senha) { this.senha = senha; return this; }
    public PessoaFisicaBuilder endereco(Endereco endereco) { this.endereco = endereco; return this; }

    public PessoaFisica build() {
        PessoaFisica pessoa = new PessoaFisica(nome, telefone, email, cpf, senha, endereco);
        pessoa.salvarDadosArquivo();
        return pessoa;
    }
}
    public static PessoaFisica criarPessoaFisica() {
        return new PessoaFisicaBuilder().build();
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
