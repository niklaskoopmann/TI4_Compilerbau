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

    private Map<DFAState, ArrayList<DFAState>> transitionMatrix;
    private DFAGenerator dfaGenerator;
    private ArrayList<String> alphabet;

    public GenericLexer(Map<DFAState, ArrayList<DFAState>> transitionMatrix){

        this.transitionMatrix = transitionMatrix;
        this.dfaGenerator = new DFAGenerator();
        this.alphabet = new ArrayList<String>(dfaGenerator.getAlphabet());
    }

    /*public boolean match(String toCheck){

        char[] toCheckArray = toCheck.toCharArray();

        DFAState currentState = dfaGenerator.getInitialState();

        for (char letter : toCheckArray) {

            DFAState nextState = transitionMatrix.get(currentState).get(alphabet.indexOf(letter + ""));

            if(nextState != null){

                currentState = nextState;
            }

            else return false;
        }

        if(currentState.isAcceptingState) return true;

        else return false;
    }*/
}
