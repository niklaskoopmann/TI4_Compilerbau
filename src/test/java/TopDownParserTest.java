import Parser.Parser;
import SyntaxTree.Visitable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Student_4
 */

class TopDownParserTest {

    private String[] regExps;
    private Parser parser;
    private Boolean[] results;

    @BeforeEach
    void setUp() {

        regExps = new String[]{
                "((a|b)*a(a|b))#",
                "(aa*a+)#",
                "(a(a|b)*a)#",
                "((abcd|abc)+)#",
                "((a|b)*a(a|b)(a|b)?(a|b)+(a|b))#",
                "(a|b*c)#",
                "((aa|bb)*((ab|ba)(aa|bb)*(ab|ba)(aa|bb)*)*)#",
                "(a*b*c*d*e)#",
                "((a|b)*a(a|b)(a|b))#",
                "(a*ba*ba*ba*)#",
                "((a|b)*abb)#",
                "((a|ab)?ba)#",
        };

        results = new Boolean[regExps.length];
    }

    @AfterEach
    void tearDown() {
        boolean works = true;
        for (int i = 0; i < results.length; i++) {
            if (!results[i]) {
                works = false;
                System.err.println("Not all expressions where parsed correctly. Error at number " + i);
            }
        }
        if (works) System.out.println("All expressions parsed successfully!");
    }

    @Test
    void start() {
        for (int i = 0; i < regExps.length; i++) {
            try {
                System.out.println("Testing parser for expression: " + regExps[i]);
                parser = new Parser(regExps[i]);
                Visitable result = parser.start();
                System.out.println("Result:");
                System.out.println(result.toString() + '\n');
                results[i] = true;
            } catch (RuntimeException e) {
                results[i] = false;
                System.err.println("Test failed for: " + regExps[i]);
            }
        }
    }
}