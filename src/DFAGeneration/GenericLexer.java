package DFAGeneration;

import java.util.ArrayList;

/**
 *
 * for whole class:
 * @author Chiara Kramer (8033039)
 *
 */

public class GenericLexer {

    private ArrayList<DFAState[]> transitionMatrix;

    public GenericLexer(ArrayList<DFAState[]> transitionMatrix){

        this.transitionMatrix = transitionMatrix;
    }

    public boolean match(String toCheck){

        char[] toCheckArray = toCheck.toCharArray();

        DFAState currentState = DFAGenerator.getInitialState();

        for (char letter : toCheckArray) {

            DFAState nextState = transitionMatrix.get(0)[DFAGenerator.getAlphabet().indexOf(letter)];

            if(nextState != null){

                currentState = nextState;
            }

            else return false;
        }

        if(currentState.isAcceptingState) return true;

        else return false;
    }
}
