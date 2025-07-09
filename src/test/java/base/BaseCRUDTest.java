package base;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import utils.AmbienteTemporario;

public abstract class BaseCRUDTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    private void iniciar() {
        AmbienteTemporario.iniciar(tempDir);
    }

    @AfterEach
    private void terminar() {
        AmbienteTemporario.terminar();
    }
    
}
