package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class AmbienteTemporario {

    static String originalArquivoUsuarios;
    static String originalArquivoFarmacias;
    static String originalArquivoMedicos;
    static String originalPathEstoque;
    
    public static void iniciar(Path tempDir){
        originalArquivoUsuarios = PessoaFisica.nomeArquivoUsuarios;
        originalArquivoFarmacias = PessoaJuridica.nomeArquivoFarmacias;
        originalPathEstoque = PessoaJuridica.basePathEstoque;
        originalArquivoMedicos = Medico.nomeArquivoMedicos;

        PessoaFisica.nomeArquivoUsuarios = tempDir.resolve("usuarios.txt").toString();
        PessoaJuridica.nomeArquivoFarmacias = tempDir.resolve("farmacias.txt").toString();
        PessoaJuridica.basePathEstoque = tempDir.resolve("estoquesFarmacias").toString();
        Medico.nomeArquivoMedicos = tempDir.resolve("medicos.txt").toString();

        try {
            Files.createDirectories(tempDir.resolve("estoquesFarmacias"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void terminar(){
        PessoaFisica.nomeArquivoUsuarios = originalArquivoUsuarios;
        PessoaJuridica.nomeArquivoFarmacias = originalArquivoFarmacias;
        Medico.nomeArquivoMedicos = originalArquivoMedicos;
        PessoaJuridica.basePathEstoque = originalPathEstoque;
    }
}
