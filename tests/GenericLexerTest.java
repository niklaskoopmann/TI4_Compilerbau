import DFAGeneration.DFAGenerator;
import DFAGeneration.DFAState;
import DFAGeneration.GenericLexer;
import Parser.TopDownParser;
import SyntaxTree.Visitable;
import Visitor.FirstVisitor;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * for whole test class:
 *
 * @author Chiara Kramer (8033039)
 */

class GenericLexerTest {

    /*

    These tests may be a little memory-hungry. Best feed it at least 8 GB of RAM. Alternatively tweak the numbers in
    lines 94 and 122.

     */

    private Map<DFAState, DFAState[]> testTransitionMatrix;
    private Map<String, Map<DFAState, DFAState[]>> testTransitionMatrixForEachRegex;
    private TopDownParser testTopDownParser;
    private FirstVisitor testFirstVisitor;
    private SecondVisitor testSecondVisitor;
    private GenericLexer testGenericLexer;
    private DFAGenerator testDFAgenerator;
    private String[] regExps;
    private HashMap<String, ArrayList<String>> acceptingWords;
    private HashMap<String, ArrayList<String>> refusingWords;

    @BeforeEach
    void setUp() {

        // test regular expressions, inspired by the Dragon Book
        regExps = new String[]{
                "(a(a|b)*a)#",
                "((a|b)*a(a|b)(a|b))#",
                "(a*ba*ba*ba*)#",
                //"((aa|bb)*((ab|ba)(aa|bb)*(ab|ba)(aa|bb)*)*)#",
                "(a|b*c)#",
                "((a|b)*abb)#",
                "((a|b)*a(a|b))#",
                "((abcd|abc)+)#",
                "((a|ab)?ba)#",
                "(aa*a+)#",
                "((a|b)*a(a|b)(a|b)?(a|b)+(a|b))#"//,
                //"(a*b*c*d*e)#"
        };

        // some random Strings accepted for each regex
        acceptingWords = new HashMap<String, ArrayList<String>>();
        refusingWords = new HashMap<String, ArrayList<String>>();

        // fill acceptingWords with values
        for (int i = 0; i < regExps.length; i++) {

            System.out.println(regExps[i]); // debug

            // parse a regex
            testTopDownParser = new TopDownParser(regExps[i]);
            Visitable syntaxTreeRootNode = testTopDownParser.start();

            // visit generated syntax tree
            // first visitor
            testFirstVisitor = new FirstVisitor();
            testFirstVisitor.visitTreeNodes(syntaxTreeRootNode);

            // second visitor
            testSecondVisitor = new SecondVisitor();
            testSecondVisitor.visitTreeNodes(syntaxTreeRootNode);

            // generate DFA from data
            testDFAgenerator = new DFAGenerator();
            testDFAgenerator.generateAlphabet(testSecondVisitor.getFollowPosTableEntries());
            testDFAgenerator.generateTransitionMatrix(testSecondVisitor.getFollowPosTableEntries());

            // get transition table and alphabet from DFA generator
            testTransitionMatrix = testDFAgenerator.getTransitionMatrix();
            SortedSet<String> testAlphabet = testDFAgenerator.getAlphabet();

            // add current regex and corresponding transition matrix to map
            testTransitionMatrixForEachRegex = new HashMap<>();
            testTransitionMatrixForEachRegex.put(regExps[i], testTransitionMatrix);

            // fill word list with a lot of random accepted words
            ArrayList<String> wordsToAccept = new ArrayList<String>();
            while (wordsToAccept.size() < 1000) {

                //int randomStringLength = (int)(Math.random() * Integer.MAX_VALUE); // takes some time...
                int randomStringLength = (int) (Math.random() * 100); // takes less time...

                String acceptedWord = "";

                // add random characters from the alphabet to acceptedWord
                for (int k = 0; k < randomStringLength; k++)
                    acceptedWord += testAlphabet.toArray()[(int) (Math.random() * testAlphabet.size()) % (testAlphabet.size() - 1) + 1];

                if (acceptedWord.matches(regExps[i].substring(1, regExps[i].length() - 2)))
                    wordsToAccept.add(acceptedWord); // assuming the System method works flawlessly
            }
            acceptingWords.put(regExps[i], wordsToAccept);

            System.out.println(wordsToAccept.size()); // debug

            // fill refusing word list with random words that do not match the regular expression
            ArrayList<String> wordsToRefuse = new ArrayList<String>();
            while (wordsToRefuse.size() < 1000) {

                //int randomStringLength = (int)(Math.random() * Integer.MAX_VALUE); // takes some time...
                int randomStringLength = (int) (Math.random() * 100); // takes less time...

                String refusedWord = "";

                // add random printable ASCII characters to the string
                for (int k = 0; k < randomStringLength; k++) refusedWord += (char) ((int) (Math.random() * 94) + 32);

                if (!refusedWord.matches(regExps[i]))
                    wordsToRefuse.add(refusedWord); // assuming the System method works flawlessly
            }
            refusingWords.put(regExps[i], wordsToRefuse);

            System.out.println(wordsToRefuse.size()); // debug
        }

        System.out.println("SETUP FINISHED!");
    }

    @Test
    void match() {

        for (String regex : regExps) {

            testTransitionMatrix = testTransitionMatrixForEachRegex.get(regex);

            for (String word : acceptingWords.get(regex)) {

                testGenericLexer = new GenericLexer(testTransitionMatrix); // reset lexer

                assertTrue(testGenericLexer.match(word));
            }

            for (String word : refusingWords.get(regex)) {

                testGenericLexer = new GenericLexer(testTransitionMatrix); // reset lexer

                assertFalse(testGenericLexer.match(word));
            }
        }
    }
}