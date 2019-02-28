package DFAGeneration;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

/**
 * for whole class:
 *
 * @author Chiara Kramer
 */

public class GenericLexer {

    //Attributes
    private SortedMap<DFAState, Map<String, DFAState>> transitionMatrix;
    private DFAGenerator dfaGenerator;
    private ArrayList<String> alphabet;

    //Constructor
    public GenericLexer(SortedMap<DFAState, Map<String, DFAState>> transitionMatrix) {

        this.transitionMatrix = transitionMatrix;
        this.dfaGenerator = new DFAGenerator();
        this.alphabet = new ArrayList<String>();
    }

    //Checks if a string is accepted by generated DFA
    public boolean match(String toCheck) {

        char[] toCheckArray = toCheck.toCharArray();

        DFAState currentState = dfaGenerator.getInitialState();

        for (char letter : toCheckArray) {

            // works since alphabet is sorted alphabetically and the state arrays in the matrix fit the alphabet sorting
            DFAState nextState = transitionMatrix.get(currentState).get(letter + "");

            if (nextState != null) currentState = nextState;
            else return false;
        }

        return currentState.isAcceptingState;
    }

    //Getter and setter

    public SortedMap<DFAState, Map<String, DFAState>> getTransitionMatrix() {
        return transitionMatrix;
    }

    public void setTransitionMatrix(SortedMap<DFAState, Map<String, DFAState>> transitionMatrix) {
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
