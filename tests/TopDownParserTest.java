import Parser.TopDownParser;
import SyntaxTree.Visitable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * for whole class:
 *
 * @author Lars Roth
 **/

class TopDownParserTest {

    private String[] regExs;
    private TopDownParser parser;
    private Boolean[] results;

    @BeforeEach
    void setUp() {
        // some valid regular expressions
        regExs = new String[]{
                "(a(a|b)*a)#",
                "((a|b)*a(a|b)(a|b))#",
                "(a*ba*ba*ba*)#",
                "((aa|bb)*((ab|ba)(aa|bb)*(ab|ba)(aa|bb)*)*)#",
                "(a|b*c)#",
                "((a|b)*abb)#",
                "((a|b)*a(a|b))#",
                "((abcd|abc)+)#",
                "((a|ab)?ba)#",
                "(aa*a+)#",
                "((a|b)*a(a|b)(a|b)?(a|b)+(a|b))#",
                "(a*b*c*d*e)#"
        };

        results = new Boolean[regExs.length];
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
        for (int i = 0; i < regExs.length; i++) {
            try {
                System.out.println("Testing parser for expression: " + regExs[i]);
                parser = new TopDownParser(regExs[i]);
                Visitable result = parser.start();
                System.out.println("Result:");
                System.out.println(result.toString() + '\n');
                results[i] = true;
            } catch (RuntimeException e) {
                results[i] = false;
                System.err.println("Test failed for: " + regExs[i]);
            }
        }
    }
}