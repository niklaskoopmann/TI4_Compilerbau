package DFAGeneration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * for whole class:
 * @author Chiara Kramer (8033039)
 *
 */

public class GenericLexer {

    private Map<DFAState, DFAState[]> transitionMatrix;
    private DFAGenerator dfaGenerator;
    private ArrayList<String> alphabet;

    public GenericLexer(Map<DFAState, DFAState[]> transitionMatrix){

        this.transitionMatrix = transitionMatrix;
        this.dfaGenerator = new DFAGenerator();
        this.alphabet = new ArrayList<String>(dfaGenerator.getAlphabet());
    }

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
