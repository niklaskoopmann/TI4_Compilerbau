import DFAGeneration.DFAGenerator;
import DFAGeneration.DFAState;
import DFAGeneration.GenericLexer;
import Parser.TopDownParser;
import SyntaxTree.Visitable;
import Visitor.FirstVisitor;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * for whole test class:
 *
 * @author Chiara Kramer
 */

class GenericLexerTest {

    /*

    These tests may be a little memory-hungry. Best feed it at least 8 GB of RAM. Alternatively tweak the numbers in
    lines 94 and 122.

     */

    private SortedMap<DFAState, Map<String, DFAState>> testTransitionMatrix;
    private Map<String, SortedMap<DFAState, Map<String, DFAState>>> testTransitionMatrixForEachRegex;
    private TopDownParser testTopDownParser;
    private FirstVisitor testFirstVisitor;
    private SecondVisitor testSecondVisitor;
    private GenericLexer testGenericLexer;
    private DFAGenerator testDFAgenerator;
    private ArrayList<String>[] testAlphabets;
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

        // some random Strings accepted for each regex
        this.acceptingWords = new HashMap<String, ArrayList<String>>();
        this.refusingWords = new HashMap<String, ArrayList<String>>();

        // init testAlphabet array
        this.testAlphabets = new ArrayList[regExps.length];

        // init testTransitionMatrix map
        testTransitionMatrixForEachRegex = new HashMap<>();

        // fill acceptingWords with values
        for (int i = 0; i < regExps.length; i++) {

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
            SortedSet<String> testAlphabetSet = testDFAgenerator.getAlphabet();
            this.testAlphabets[i] = new ArrayList<String>();
            this.testAlphabets[i].addAll(testAlphabetSet);

            // add current regex and corresponding transition matrix to map
            testTransitionMatrixForEachRegex.put(regExps[i], testTransitionMatrix);

            // fill word list with a lot of random accepted words
            ArrayList<String> wordsToAccept = new ArrayList<String>();
            while (wordsToAccept.size() < 10000) {

                //int randomStringLength = (int)(Math.random() * Integer.MAX_VALUE); // takes some time...
                int randomStringLength = (int) (Math.random() * 1000); // takes less time...

                String acceptedWord = "";

                // add random characters from the alphabet to acceptedWord
                for (int k = 0; k < randomStringLength; k++)
                    acceptedWord += testAlphabetSet.toArray()[(int) (Math.random() * testAlphabetSet.size()) % (testAlphabetSet.size() - 1) + 1];

                if (acceptedWord.matches(regExps[i].substring(1, regExps[i].length() - 2)))
                    wordsToAccept.add(acceptedWord); // assuming the System method works flawlessly
            }
            acceptingWords.put(regExps[i], wordsToAccept);

            // fill refusing word list with random words that do not match the regular expression
            ArrayList<String> wordsToRefuse = new ArrayList<String>();
            while (wordsToRefuse.size() < 10000) {

                //int randomStringLength = (int)(Math.random() * Integer.MAX_VALUE); // takes some time...
                int randomStringLength = (int) (Math.random() * 1000); // takes less time...

                String refusedWord = "";

                // add random printable ASCII characters to the string
                for (int k = 0; k < randomStringLength; k++) refusedWord += (char) ((int) (Math.random() * 94) + 32);

                if (!refusedWord.matches(regExps[i]))
                    wordsToRefuse.add(refusedWord); // assuming the System method works flawlessly
            }
            refusingWords.put(regExps[i], wordsToRefuse);
        }
    }

    @Test
    void match() {

        for (String regex : regExps) {

            testTransitionMatrix = testTransitionMatrixForEachRegex.get(regex);

            for (String word : acceptingWords.get(regex)) {

                testGenericLexer = new GenericLexer(testTransitionMatrix); // reset lexer
                testGenericLexer.setDfaGenerator(testDFAgenerator);
                testGenericLexer.setAlphabet(testAlphabets[Arrays.asList(regExps).indexOf(regex)]);
                testGenericLexer.setTransitionMatrix(testTransitionMatrix);

                assertTrue(testGenericLexer.match(word + "#"));
            }

            for (String word : refusingWords.get(regex)) {

                testGenericLexer = new GenericLexer(testTransitionMatrix); // reset lexer
                testGenericLexer.setDfaGenerator(testDFAgenerator);
                testGenericLexer.setAlphabet(testAlphabets[Arrays.asList(regExps).indexOf(regex)]);
                testGenericLexer.setTransitionMatrix(testTransitionMatrix);

                assertFalse(testGenericLexer.match(word));
            }
        }
    }
}