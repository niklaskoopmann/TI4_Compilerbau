package DFAGeneration;

import java.util.Map;

/**
 * @author Student_1
 */

public class GenericLexer {

    private Map<DFAState, Map<String, DFAState>> dfa;

    private DFAState state;
    private String[] word;
    private String letter;

    /**
     * Creates a instance of the Lexer
     * @param generator the DFA generator containing the matrix and the start state
     */
    public GenericLexer(DFAGenerator generator) {
        dfa = generator.getDFA();
        state = generator.getStart();
    }

    /**
     * Checks if the String is accepted
     * @param input the string to be checked
     * @return whether the String is accepted or not
     */
    public boolean match(String input) {
        word = input.split("");
        for (int i = 0; i < word.length; i++) {
            letter = word[i];
            if (dfa.get(state).containsKey(letter))
                state = dfa.get(state).get(letter);
            else
                return false;
        }
        return state.isAcceptingState;
    }

}