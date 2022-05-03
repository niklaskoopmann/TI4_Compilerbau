import DFAGeneration.DFAGenerator;
import DFAGeneration.DFAState;
import DFAGeneration.FollowPosTableEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Student_1
 */

public class DFAGeneratorTest {

    private Set<String> alphabet01;
    private SortedMap<Integer, FollowPosTableEntry> followposTableEntries01;
    private Set<Integer> firstpos_01;
    private Map<DFAState, Map<String, DFAState>> stateTransitionTable01;
    private DFAState finalState;
    private DFAState firstState;

    /**
     * Generates the Input and the expected output before each test
     */
    @BeforeEach
    public void setup() {
        FollowPosTableEntry followentry;
        firstpos_01 = new HashSet<>(Arrays.asList(1, 2, 3));

        followposTableEntries01 = new TreeMap<>();

        alphabet01 = new HashSet<>();
        alphabet01.addAll(Arrays.asList("a", "b"));

        followentry = new FollowPosTableEntry(1, "a");
        followentry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTableEntries01.put(1, followentry);

        followentry = new FollowPosTableEntry(2, "b");
        followentry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTableEntries01.put(2, followentry);

        followentry = new FollowPosTableEntry(3, "a");
        followentry.followpos.add(4);
        followposTableEntries01.put(3, followentry);

        followentry = new FollowPosTableEntry(4, "b");
        followentry.followpos.add(5);
        followposTableEntries01.put(4, followentry);

        followentry = new FollowPosTableEntry(5, "b");
        followentry.followpos.add(6);
        followposTableEntries01.put(5, followentry);

        followentry = new FollowPosTableEntry(6, "#");
        followposTableEntries01.put(6, followentry);


        stateTransitionTable01 = new HashMap<>();
        DFAState s123 = firstState = new DFAState(1, false, new HashSet<>(Arrays.asList(1, 2, 3)));
        DFAState s1234 = new DFAState(2, false, new HashSet<>(Arrays.asList(1, 2, 3, 4)));
        DFAState s1235 = new DFAState(3, false, new HashSet<>(Arrays.asList(1, 2, 3, 5)));
        DFAState s1236 = finalState = new DFAState(4, true, new HashSet<>(Arrays.asList(1, 2, 3, 6)));

        Map<String, DFAState> t123 = new HashMap<>();
        t123.put("a", s1234);
        t123.put("b", s123);

        Map<String, DFAState> t1234 = new HashMap<>();
        t1234.put("a", s1234);
        t1234.put("b", s1235);

        Map<String, DFAState> t1235 = new HashMap<>();
        t1235.put("a", s1234);
        t1235.put("b", s1236);

        Map<String, DFAState> t1236 = new HashMap<>();
        t1236.put("a", s1234);
        t1236.put("b", s123);

        stateTransitionTable01.put(s123, t123);
        stateTransitionTable01.put(s1234, t1234);
        stateTransitionTable01.put(s1235, t1235);
        stateTransitionTable01.put(s1236, t1236);

    }

    /**
     * Validating the start State
     */
    @Test
    public void getStart() {
        DFAGenerator testGenerator = new DFAGenerator(firstpos_01, followposTableEntries01);

        assertEquals(firstState, testGenerator.getStart());
        assertEquals(firstState.index, testGenerator.getStart().index);
        assertEquals(firstState.isAcceptingState, testGenerator.getStart().isAcceptingState);
    }

    /**
     * Validates the generated Alphabet
     */
    @Test
    public void getAlphabet() {
        DFAGenerator testGenerator = new DFAGenerator(firstpos_01, followposTableEntries01);
        assertEquals(alphabet01, testGenerator.getAlphabet());
    }

    /**
     * Chech the Sematic equivalences
     */
    @Test
    public void getDFA_equivalence() {
        DFAGenerator testGenerator = new DFAGenerator(firstpos_01, followposTableEntries01);
        assertEquals(stateTransitionTable01, testGenerator.getDFA());
    }

    /**
     * Validate the final State
     */
    @Test
    public void getDFA_finalStates() {
        DFAGenerator testGenerator = new DFAGenerator(firstpos_01, followposTableEntries01);

        List<DFAState> testFinalStates = new LinkedList<>();

        for (DFAState testState : testGenerator.getDFA().keySet()) {
            if (testState.isAcceptingState) {
                testFinalStates.add(testState);
            }
        }

        assertEquals(1, testFinalStates.size());
        assertEquals(finalState, testFinalStates.get(0));

    }

    /**
     * Validate the expected execution order
     */
    @Test
    public void getDFA_exact() {
        DFAGenerator testGenerator = new DFAGenerator(firstpos_01, followposTableEntries01);

        Map<Integer, DFAState> states = new HashMap<>();
        testGenerator.getDFA().keySet().forEach((dfaState) -> {
            states.put(dfaState.index, dfaState);
        });

        stateTransitionTable01.keySet().forEach(
                (state) -> assertEquals(state, states.get(state.index))
        );
    }
}
