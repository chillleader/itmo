import functions.FunctionSystem;
import functions.logarithmic.Ln;
import functions.logarithmic.Log10;
import functions.logarithmic.Log2;
import functions.logarithmic.Log5;
import functions.trigonometric.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicFunctionsTest {

    @Test
    public void testSin() {
        Sin sin = new Sin();

        double result = sin.value(1);
        assertEquals(0.8414, result, 0.01);

        result = sin.value(-0.5);
        assertEquals(-0.4794255386, result, 0.01);

        sin.printValues(-3.14, 0.01, 628);
    }

    @Test
    public void testLn() {
        Ln ln = new Ln();

        double result = ln.value(1);
        assertEquals(0, result, 0.01);

        result = ln.value(5);
        assertEquals(1.609, result, 0.01);

        ln.printValues(0, 0.1, 100);
    }

    @Test
    public void printAllValues() {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        cos.printValues(-3.14, 0.01, 628);
        Tan tan = new Tan(sin, cos);
        tan.printValues(-3.14, 0.01, 628);
        Cot cot = new Cot(sin, cos);
        cot.printValues(-3.14, 0.01, 628);
        Sec sec = new Sec(cos);
        sec.printValues(-3.14, 0.01, 628);
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);
        Log5 log5 = new Log5(ln);
        Log10 log10 = new Log10(ln);
        log2.printValues(0, 0.1, 100);
        log5.printValues(0, 0.1, 100);
        log10.printValues(0, 0.1, 100);
        FunctionSystem functionSystem = new FunctionSystem(cos, sec, tan, cot, log2, log5, log10);
        functionSystem.printValues(-2, 0.01, 400);
    }
}
