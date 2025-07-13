package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import backend.Endereco;
import backend.farmacia.Estoque;
import backend.farmacia.PessoaJuridica;

public class FixedFarmacia {
    public static PessoaJuridica resgatarFarmaciaArquivo(PessoaJuridica farmacia, Boolean ignorarSenha, Boolean ignorarAgenda){
        try{
            FileReader fr = new FileReader(PessoaJuridica.nomeArquivoFarmacias);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();

            while (linha != null){
                String[] dadosLinha = linha.split(",");
                String email = dadosLinha[2];
                String senha = dadosLinha[3];

                if (email.equals(farmacia.getEmail()) && (ignorarSenha == true || senha.equals(farmacia.getSenha()))){
                    String telefone = dadosLinha[1];
                    String nome = dadosLinha[0];
                    String cnpj = dadosLinha[4];
                    Endereco endereco = Endereco.stringToEndereco(dadosLinha[5]);
                    PessoaJuridica recuperado = new PessoaJuridica(nome, telefone, email, senha, cnpj, endereco);

                    File arquivoEstoque = new File(farmacia.getNomeArquivoEstoque());
                    if(arquivoEstoque.exists()){
                        Estoque estoque = PessoaJuridica.resgatarEstoqueArquivo(farmacia.getNomeArquivoEstoque());
                        recuperado.setEstoque(estoque,false);
                    }
                    br.close();
                    return recuperado;
                }
                linha = br.readLine();
            }
            br.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
    }
}
}
