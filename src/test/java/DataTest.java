import backend.gerenciamento.Data;
import backend.usuario.Uso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataTest {

    private Calendar mockedCalendar;

    @BeforeEach
    public void setup() {
        mockedCalendar = mock(Calendar.class);
        when(mockedCalendar.get(Calendar.DAY_OF_WEEK)).thenReturn(2);
        when(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(10);
    }

    @Test
    public void testFormatarDia() {
        assertEquals(2, Data.formatarDia("seg"));
        assertEquals(3, Data.formatarDia("ter"));
        assertEquals(4, Data.formatarDia("qua"));
        assertEquals(5, Data.formatarDia("qui"));
        assertEquals(6, Data.formatarDia("sex"));
        assertEquals(7, Data.formatarDia("sab"));
        assertEquals(1, Data.formatarDia("dom"));
    }

    @Test
    public void testFormatarDiaInvalido(){
        assertEquals(1, Data.formatarDia("fwefwafwefe"));
        assertEquals(1, Data.formatarDia(""));
        assertEquals(1, Data.formatarDia("1"));
        assertThrows(NullPointerException.class, () -> { Data.formatarDia(null);});
    }
    
    
    @Test
    public void testVerificarDia() {
        // Simula que hoje é segunda-feira (Calendar.DAY_OF_WEEK = 2)
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            ArrayList<String> dias = new ArrayList<>();
            dias.add("seg");

            assertTrue(Data.verificarDia(dias));

            dias.clear();
            dias.add("ter");
            assertFalse(Data.verificarDia(dias));

            dias.clear();
            dias.add("ter");
            dias.add("qua");
            dias.add("qui");
            dias.add("sex");
            dias.add("sab");
            dias.add("dom");
            assertFalse(Data.verificarDia(dias));
        }
    }

    @Test void testVerificarDiaInvalido() {
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            ArrayList<String> dias = new ArrayList<>();

            dias.clear();
            dias.add("abc");
            assertFalse(Data.verificarDia(dias));

            dias.clear();
            dias.add("1");
            assertFalse(Data.verificarDia(dias));

            dias.clear();
            assertFalse(Data.verificarDia(dias));

            assertThrows(NullPointerException.class, () -> { Data.verificarDia(null);});
        }
    }

    @Test
    public void testVerificarHora(){
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            assertTrue(Data.verificarHora(10));
            assertFalse(Data.verificarHora(11));
            when(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(0);

            // Não entendi o uso do ultimaVerficacaoHorario mas quando a hora é 0 verificação hora SEMPRE é false
            assertFalse(Data.verificarHora(0));
        }
    }

    @Test
    public void testVerificarHoraInvalida() {
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            assertFalse(Data.verificarHora(-1));
            assertFalse(Data.verificarHora(24));
            assertFalse(Data.verificarHora(25));
            assertFalse(Data.verificarHora(100));
            assertThrows(NullPointerException.class, () -> { Data.verificarHora(null);});
        }
    }

    @Test
    public void testEhMeiaNoite() {

        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            assertFalse(Data.ehMeiaNoite());
            when(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(0);
            assertTrue(Data.ehMeiaNoite());
        }
    }

    @Test
    public void testHoraDoRemedio() {
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

                        // Além disso eu não sei porque getHorarios() de uso retornaria os dias porque eu não me aprofundei na implementação
            ArrayList<String> dias = new ArrayList<>();
            dias.add("seg"); // Segunda-feira
            Uso uso = new Uso(null, 0, dias , 0, 0, 0, 0);

            assertTrue(Data.horaDoRemedio(uso,10));
            assertFalse(Data.horaDoRemedio(uso,11));

            // Não entendi o uso do ultimaVerficacaoHorario mas quando a hora é 0 verificação hora SEMPRE é false
            assertFalse(Data.horaDoRemedio(uso,0));
            dias.clear();
            dias.add("ter");
            uso.setHorarios(dias);
            assertFalse(Data.horaDoRemedio(uso,10));
        }
    }

    @Test
    public void testHoraDoRemedioInvalida() {
        try (MockedStatic<Calendar> mocked = mockStatic(Calendar.class)) {
            mocked.when(Calendar::getInstance).thenReturn(mockedCalendar);

            ArrayList<String> dias = new ArrayList<>();
            dias.add("seg");
            Uso uso = new Uso(null, 0, dias , 0, 0, 0, 0);

            assertFalse(Data.horaDoRemedio(uso,-1));
            assertFalse(Data.horaDoRemedio(uso,24));
            assertFalse(Data.horaDoRemedio(uso,25));
            assertFalse(Data.horaDoRemedio(uso,100));
            Uso uso2 = new Uso(null, 0, null, 0, 0);
            assertThrows(NullPointerException.class, () -> { Data.horaDoRemedio(uso2,10);});
            assertThrows(NullPointerException.class, () -> { Data.horaDoRemedio(uso2,null);});
            assertThrows(NullPointerException.class, () -> { Data.horaDoRemedio(null,10);});
            assertThrows(NullPointerException.class, () -> { Data.horaDoRemedio(null,null);});
        }

    }
}