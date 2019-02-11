import DFAGeneration.DFAState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * for whole test class:
 * @author Chiara Kramer (8033039)
 *
 */

class GenericLexerTest {

    private ArrayList<DFAState[]> testTransitionMatrix;
    private SortedSet<String> randomTestAlphabet;

    @BeforeEach
    void setUp() {

        testTransitionMatrix = new ArrayList<DFAState[]>();

        // generate random alphabet
        randomTestAlphabet = new TreeSet<String>();
        int numberOfLettersInTestAlphabet = (int)(Math.random() * Integer.MAX_VALUE);
        for(int i = 0; i < numberOfLettersInTestAlphabet; i++){
            // 32 ... 126 (ASCII values with character representation)
            String randomASCIIChar = (char)(32 + (int)(Math.random() * 94)) + "";
            randomTestAlphabet.add(randomASCIIChar);
            // update transition matrix simultaneously
            testTransitionMatrix.get(i + 1)[0] = ;
        }


    }

    @Test
    void match() {


    }
}