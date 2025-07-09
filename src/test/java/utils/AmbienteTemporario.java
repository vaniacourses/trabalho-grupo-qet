package utils;

import java.nio.file.Path;

import org.junit.jupiter.api.io.TempDir;

import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class AmbienteTemporario {

    static String originalArquivoUsuarios;
    static String originalArquivoFarmacias;
    static String originalArquivoMedicos;
    
    public static void iniciar(Path tempDir){
        originalArquivoUsuarios = PessoaFisica.nomeArquivoUsuarios;
        originalArquivoFarmacias = PessoaJuridica.nomeArquivoFarmacias;
        originalArquivoMedicos = Medico.nomeArquivoMedicos;

        PessoaFisica.nomeArquivoUsuarios = tempDir.resolve("usuarios.txt").toString();
        PessoaJuridica.nomeArquivoFarmacias = tempDir.resolve("farmacias.txt").toString();
        Medico.nomeArquivoMedicos = tempDir.resolve("medicos.txt").toString();

    }

    public static void terminar(){
        PessoaFisica.nomeArquivoUsuarios = originalArquivoUsuarios;
        PessoaJuridica.nomeArquivoFarmacias = originalArquivoFarmacias;
        Medico.nomeArquivoMedicos = originalArquivoMedicos;
    }
}
