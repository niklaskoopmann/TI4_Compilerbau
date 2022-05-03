package DFAGeneration;
import java.util.*;

/**
 * @author Student_1
 */

public class DFAGenerator {

    private final Set<String> alphabetSet;
    private final SortedMap<Integer, FollowPosTableEntry> followposMap;
    private Set<Integer> firstpos;
    private int finalpos;
    private Map<Set<Integer>, DFAState> existingStates = new HashMap<>();
    private Map<DFAState, Map<String, DFAState>> dfa;

    private int stateIndexCounter = 1;

    public DFAGenerator(Set<Integer> firstpos, SortedMap<Integer, FollowPosTableEntry> followpositions) {
        this.followposMap = followpositions;
        this.alphabetSet = generateAlphabet();

        this.firstpos = firstpos;
        this.finalpos = this.followposMap.lastKey();
        this.dfa = new HashMap<>();

        createDFA();
    }

    /**
     * Created the DFA from the Input
     */
    private void createDFA() {
        DFAState baseState;
        Set<Integer> nextStateSet;
        DFAState followingState;
        Queue<DFAState> unmarkedStates = new LinkedList<>();

        unmarkedStates.add(getState(firstpos));

        do {
            baseState = unmarkedStates.remove();
            Map<String, DFAState> transitions = new HashMap<>();

            for (String input : alphabetSet) {

                nextStateSet = getNextState(baseState, input.toString());
                if (nextStateSet.isEmpty()) {
                    followingState = null;
                } else {
                    followingState = getState(nextStateSet);
                }

                if (!dfa.containsKey(followingState) && !unmarkedStates.contains(followingState) && (followingState != null)) {
                    unmarkedStates.add(followingState);
                }

                transitions.put(input, followingState);
            }

            dfa.put(baseState, transitions);

        } while (!unmarkedStates.isEmpty());
    }

    /**
     * @param stateSet indexes of the state
     * @return the parsed DFA
     */
    private DFAState getState(Set<Integer> stateSet) {
        DFAState state = existingStates.get(stateSet);
        if (state != null) return state;

        boolean isEndState = stateSet.contains(finalpos);
        state = new DFAState(stateIndexCounter++, isEndState, stateSet);

        existingStates.put(stateSet, state);
        return state;
    }

    /**
     * @param state start
     * @param input the input Regex
     * @return all state Positions
     */
    private Set<Integer> getNextState(DFAState state, String input) {

        Set<Integer> followpos = new HashSet<>();

        for (Integer position : state.positionsSet) {
            FollowPosTableEntry transition = followposMap.get(position);
            if (String.valueOf(transition.symbol.charAt(0)).equals(input))
                followpos.addAll(transition.followpos);
        }

        return followpos;
    }

    /**
     * @return Generates the Alphabet of the DFA
     * @throws IllegalArgumentException When the String is too long or the termination symbol is missing
     */
    private Set<String> generateAlphabet() {
        Set<String> alphabet = new HashSet<>();

        this.followposMap.forEach((id, followpos) -> {
            if (followpos.symbol != null && followpos.symbol.length() != 1) {
                throw new IllegalArgumentException("Invalid folloposTable! Transitions may only contain a single Character as symbol");
            }
            if (followpos.symbol != null) alphabet.add(String.valueOf(followpos.symbol.charAt(0)));
        });

        if (!alphabet.remove("#")) {
            throw new IllegalArgumentException("Invalid folloposTable! Must contain the end symbol");
        }

        return alphabet;
    }

    public Set<String> getAlphabet() {
        return alphabetSet;
    }

    public DFAState getStart() {
        return existingStates.get(firstpos);
    }

    public Map<DFAState, Map<String, DFAState>> getDFA() {
        return this.dfa;
    }
}