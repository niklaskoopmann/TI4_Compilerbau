package DFAGeneration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * for whole class:
 * @author Chiara Kramer
 *
 */

public class GenericLexer {

    //Attributes
    private Map<DFAState, DFAState[]> transitionMatrix;
    private DFAGenerator dfaGenerator;
    private ArrayList<String> alphabet;

    //Constructor
    public GenericLexer(Map<DFAState, DFAState[]> transitionMatrix){

        this.transitionMatrix = transitionMatrix;
        this.dfaGenerator = new DFAGenerator();
        this.alphabet = new ArrayList<String>(dfaGenerator.getAlphabet());
    }

    //Checks if a string is accepted by generated DFA
    public boolean match(String toCheck){

        char[] toCheckArray = toCheck.toCharArray();

        DFAState currentState = dfaGenerator.getInitialState();

        for (char letter : toCheckArray) {

            DFAState nextState = transitionMatrix.get(currentState)[alphabet.indexOf(letter + "")];

            if(nextState != null){

                currentState = nextState;
            }

            else return false;
        }

        if(currentState.isAcceptingState) return true;

        else return false;
    }

    //Getter and setter

    public Map<DFAState, DFAState[]> getTransitionMatrix() {
        return transitionMatrix;
    }

    public void setTransitionMatrix(Map<DFAState, DFAState[]> transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }

    public DFAGenerator getDfaGenerator() {
        return dfaGenerator;
    }

    public void setDfaGenerator(DFAGenerator dfaGenerator) {
        this.dfaGenerator = dfaGenerator;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }
}
