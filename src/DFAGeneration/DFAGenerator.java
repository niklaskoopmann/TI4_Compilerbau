package DFAGeneration;

import java.util.*;

public class DFAGenerator {

    private SortedSet<String> alphabet;
    private DFAState initialState;
    private Map<DFAState, DFAState[]> transitionMatrix;

    public DFAGenerator() {
        this.alphabet = new TreeSet<String>();
        this.transitionMatrix = new HashMap<DFAState, DFAState[]>();

        // CAUTION: Initialize ALL ArrayLists in Map with the SAME LENGTH, i. e. the number of ELEMENTS IN THE ALPHABET!
    }

    public SortedSet<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(SortedSet<String> alphabet) {
        this.alphabet = alphabet;
    }

    public DFAState getInitialState() {
        return initialState;
    }

    public void setInitialState(DFAState initialState) {
        this.initialState = initialState;
    }

    public Map<DFAState, DFAState[]> getTransitionMatrix() {
        return transitionMatrix;
    }

    public void setTransitionMatrix(Map<DFAState, DFAState[]> transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }
}
