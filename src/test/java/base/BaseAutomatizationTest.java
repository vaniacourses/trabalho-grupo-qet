package base;

import java.util.concurrent.TimeUnit;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import inicio.MedAlerta;

public abstract class BaseAutomatizationTest extends BaseCRUDTest {

    protected FrameFixture inicioWindow;
    protected Robot robot;

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

    protected FrameFixture findFrame(Class<?> frameClass) {
    return WindowFinder.findFrame(frameClass.getSimpleName())
                       .withTimeout(5, TimeUnit.SECONDS)
                       .using(robot);
    }
    
}
