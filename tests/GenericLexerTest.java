import DFAGeneration.DFAGenerator;
import DFAGeneration.DFAState;
import DFAGeneration.GenericLexer;
import Parser.TopDownParser;
import SyntaxTree.SyntaxNode;
import SyntaxTree.Visitable;
import Visitor.FirstVisitor;
import Visitor.SecondVisitor;
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

    private Map<DFAState, DFAState[]> testTransitionMatrix;
    private GenericLexer testGenericLexer;
    private String[] regExps;

    /*

    A little stuck right now. Options:
    -   Assuming the components prior to GenericLexer work fine, it should be possible to define a regular expression,
        have it interpreted and run match() to see whether GenericLexer has flaws.
        ->  traverse DFA randomly in order to find random strings matching given regex

     */

    @BeforeEach
    void setUp() { // todo declare other components as attributes

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
        HashMap<String, ArrayList<String>> acceptingWords = new HashMap<String, ArrayList<String>>();

        // fill acceptingWords with values
        for(int i = 0; i < regExps.length; i++){

            // parse a regex
            TopDownParser testTopDownParser = new TopDownParser(regExps[i]);
            Visitable syntaxTreeRootNode = testTopDownParser.start();

            // visit generated syntax tree
                // first visitor
            FirstVisitor testFirstVisitor = new FirstVisitor();
            testFirstVisitor.visitTreeNodes(syntaxTreeRootNode);

                // second visitor
            SecondVisitor testSecondVisitor = new SecondVisitor();
            testSecondVisitor.visitTreeNodes(syntaxTreeRootNode);

            // generate DFA from data
            DFAGenerator testDFAgenerator = new DFAGenerator();
            testDFAgenerator.doSomething();

            // get transition table from DFA generator
            Map<DFAState, DFAState[]> testTransitionMatrix = testDFAgenerator.getTransitionMatrix();

            ArrayList<String> words = new ArrayList<String>();
            for(int j = 0; j < 100000; j++){

            }
            acceptingWords.
        }





        /*testTransitionMatrix = new HashMap<DFAState, ArrayList<DFAState>>();

        // generate random alphabet
        Set<String> randomTestAlphabet = new TreeSet<String>();
        int numberOfLettersInTestAlphabet = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        for(int i = 0; i < numberOfLettersInTestAlphabet; i++){
            // 32 ... 126 (ASCII values with character representation)
            String randomASCIIChar = (char)(32 + (int)(Math.random() * 94)) + "";
            randomTestAlphabet.add(randomASCIIChar);
        }

        // generate random DFA states
        int numberOfTestStates = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        for(int i = 0; i < numberOfTestStates; i++){
            DFAState testDFAstate = new DFAState(i, Math.random() > 0.5, );
            testTransitionMatrix.put(testDFAstate, )
        }*/
    }

    @Test
    void match() {


    }
}