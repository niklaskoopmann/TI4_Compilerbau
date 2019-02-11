package Parser;

import SyntaxTree.Visitable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopDownParserTest {

    // (a|b)*aab
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void start() {
        String regEx = "(a|b)*aab";
        TopDownParser parser = new TopDownParser(regEx);
        Visitable result = parser.start(null);
        System.out.println(result.toString());
    }
}