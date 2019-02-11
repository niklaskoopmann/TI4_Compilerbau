package DFAGeneration;

import java.util.SortedSet;
import java.util.TreeSet;

public class DFAGenerator {

    private SortedSet<String> alphabet;
    private DFAState initialState;

    public DFAGenerator() {
        this.alphabet = new TreeSet<String>();
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
}
