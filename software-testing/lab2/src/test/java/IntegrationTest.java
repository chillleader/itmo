import functions.FunctionSystem;
import functions.logarithmic.Ln;
import functions.logarithmic.Log10;
import functions.logarithmic.Log2;
import functions.logarithmic.Log5;
import functions.trigonometric.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    private static final double TEST_VALUE_POS = 0.5;
    private static final double TEST_VALUE_NEG = -0.5;

    private static final double COS_NEG = 0.87758256189;
    private static final double SIN_NEG = -0.4794255386;
    private static final double TAN_NEG = -0.54630248984;
    private static final double COT_NEG = -1.83048772171;
    private static final double SEC_NEG = 1.13949392732;
    private static final double LN_POS = -0.69314718056;
    private static final double LOG10_POS = -0.30102999566;
    private static final double LOG5_POS = -0.4306765581;
    private static final double LOG2_POS = -1d;

    // MOCKS
    private Cos cos;
    private Sin sin;
    private Tan tan;
    private Cot cot;
    private Sec sec;
    private Log10 log10;
    private Log5 log5;
    private Log2 log2;
    private Ln ln;

    @BeforeEach
    public void prepareMocks() {
        cos = mock(Cos.class);
        when(cos.value(TEST_VALUE_NEG)).thenReturn(COS_NEG);
        tan = mock(Tan.class);
        when(tan.value(TEST_VALUE_NEG)).thenReturn(TAN_NEG);
        cot = mock(Cot.class);
        when(cot.value(TEST_VALUE_NEG)).thenReturn(COT_NEG);
        sec = mock(Sec.class);
        when(sec.value(TEST_VALUE_NEG)).thenReturn(SEC_NEG);
        sin = mock(Sin.class);
        when(sin.value(TEST_VALUE_NEG)).thenReturn(SIN_NEG);

        log2 = mock(Log2.class);
        when(log2.value(TEST_VALUE_POS)).thenReturn(LOG2_POS);
        log5 = mock(Log5.class);
        when(log5.value(TEST_VALUE_POS)).thenReturn(LOG5_POS);
        log10 = mock(Log10.class);
        when(log10.value(TEST_VALUE_POS)).thenReturn(LOG10_POS);
        ln = mock(Ln.class);
        when(ln.value(5)).thenReturn(1.60943791243);
        when(ln.value(2)).thenReturn(0.69314718056);
        when(ln.value(10)).thenReturn(2.30258509299);
        when(ln.value(TEST_VALUE_POS)).thenReturn(LN_POS);

    }

    /**
     * First we test FunctionSystem with all mocks
     */
    @Test
    @Order(1)
    public void testFunctionSystem() {
        FunctionSystem functionSystem = new FunctionSystem(cos, sec, tan, cot, log2, log5, log10);
        double result = functionSystem.value(0.5);
        verify(log2, times(1)).value(0.5);
        verify(log5, times(2)).value(0.5);
        verify(log10, times(2)).value(0.5);
        assertEquals(-0.748515, result, 0.01);

        result = functionSystem.value(TEST_VALUE_NEG);
        verify(cos,times(1)).value(TEST_VALUE_NEG);
        verify(tan, times(1)).value(TEST_VALUE_NEG);
        verify(cot, times(1)).value(TEST_VALUE_NEG);
        verify(sec, times(1)).value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(2)
    public void testWithCos() {
        Cos rCos = new Cos(sin);
        FunctionSystem functionSystem = new FunctionSystem(rCos, sec, tan, cot, log2, log5, log10);
        double result = functionSystem.value(TEST_VALUE_NEG);
        verify(sin,times(1)).value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(3)
    public void testWithTan() {
        Cos rCos = spy(new Cos(sin));
        Tan rTan = new Tan(sin, rCos);
        FunctionSystem functionSystem = new FunctionSystem(rCos, sec, rTan, cot, log2, log5, log10);
        double result = functionSystem.value(TEST_VALUE_NEG);
        verify(sin,times(3)).value(TEST_VALUE_NEG);
        verify(rCos,times(2)).value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(4)
    public void testWithCot() {
        Cos rCos = spy(new Cos(sin));
        Tan rTan = new Tan(sin, rCos);
        Cot rCot = new Cot(sin, rCos);
        FunctionSystem functionSystem = new FunctionSystem(rCos, sec, rTan, rCot, log2, log5, log10);
        double result = functionSystem.value(TEST_VALUE_NEG);
        verify(sin,times(5)).value(TEST_VALUE_NEG);
        verify(rCos,times(3)).value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(5)
    public void testWithSec() {
        Cos rCos = spy(new Cos(sin));
        Tan rTan = new Tan(sin, rCos);
        Cot rCot = new Cot(sin, rCos);
        Sec rSec = new Sec(rCos);
        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, log2, log5, log10);
        double result = functionSystem.value(TEST_VALUE_NEG);
        verify(sin,times(6)).value(TEST_VALUE_NEG);
        verify(rCos,times(4)).value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(6)
    public void testWithLog10() {
        Cos rCos = new Cos(sin);
        Tan rTan = new Tan(sin, rCos);
        Cot rCot = new Cot(sin, rCos);
        Sec rSec = new Sec(rCos);
        Log10 rLog10 = new Log10(ln);

        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, log2, log5, rLog10);
        double result = functionSystem.value(TEST_VALUE_POS);
        verify(ln,times(2)).value(TEST_VALUE_POS);
        verify(ln, times(2)).value(10);
        assertEquals(-0.748515, result, 0.01);
    }

    @Test
    @Order(7)
    public void testWithLog2() {
        Cos rCos = new Cos(sin);
        Tan rTan = new Tan(sin, rCos);
        Cot rCot = new Cot(sin, rCos);
        Sec rSec = new Sec(rCos);
        Log10 rLog10 = new Log10(ln);
        Log2 rLog2 = new Log2(ln);

        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, rLog2, log5, rLog10);
        double result = functionSystem.value(TEST_VALUE_POS);
        verify(ln,times(3)).value(TEST_VALUE_POS);
        verify(ln, times(1)).value(2);
        assertEquals(-0.748515, result, 0.01);
    }

    @Test
    @Order(8)
    public void testWithLog5() {
        Cos rCos = new Cos(sin);
        Tan rTan = new Tan(sin, rCos);
        Cot rCot = new Cot(sin, rCos);
        Sec rSec = new Sec(rCos);
        Log10 rLog10 = new Log10(ln);
        Log2 rLog2 = new Log2(ln);
        Log5 rLog5 = new Log5(ln);

        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, rLog2, rLog5, rLog10);
        double result = functionSystem.value(TEST_VALUE_POS);
        verify(ln,times(5)).value(TEST_VALUE_POS);
        verify(ln, times(2)).value(5);
        assertEquals(-0.748515, result, 0.01);
    }

    @Test
    @Order(9)
    public void testWithSin() {
        Sin rSin = new Sin();
        Cos rCos = new Cos(rSin);
        Tan rTan = new Tan(rSin, rCos);
        Cot rCot = new Cot(rSin, rCos);
        Sec rSec = new Sec(rCos);
        Log10 rLog10 = new Log10(ln);
        Log2 rLog2 = new Log2(ln);
        Log5 rLog5 = new Log5(ln);
        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, rLog2, rLog5, rLog10);
        double result = functionSystem.value(TEST_VALUE_NEG);
        assertEquals(0.000738955, result, 0.01);
    }

    @Test
    @Order(10)
    public void testWithLn() {
        Sin rSin = new Sin();
        Cos rCos = new Cos(rSin);
        Tan rTan = new Tan(rSin, rCos);
        Cot rCot = new Cot(rSin, rCos);
        Sec rSec = new Sec(rCos);
        Ln rLn = new Ln();
        Log10 rLog10 = new Log10(rLn);
        Log2 rLog2 = new Log2(rLn);
        Log5 rLog5 = new Log5(rLn);
        FunctionSystem functionSystem = new FunctionSystem(rCos, rSec, rTan, rCot, rLog2, rLog5, rLog10);
        double result = functionSystem.value(TEST_VALUE_POS);
        assertEquals(-0.748515, result, 0.01);
    }
}
