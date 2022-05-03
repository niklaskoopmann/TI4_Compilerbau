import DFAGeneration.DFAGenerator;
import DFAGeneration.DFAState;
import DFAGeneration.FollowPosTableEntry;
import DFAGeneration.GenericLexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Student_1
 */

class GenericLexerTest {

    private Set<Character> alphabet01;
    private SortedMap<Integer, FollowPosTableEntry> followposTableEntries01;
    private Set<Integer> firstpos_01;
    private Map<DFAState, Map<Character, DFAState>> stateTransitionTable01;
    private DFAState finalState;
    private DFAState firstState;
    private GenericLexer genericLexer;

    @BeforeEach
    public void setUp() {
        FollowPosTableEntry followentry;
        firstpos_01 = new HashSet<>(Arrays.asList(1,2,3));

        followposTableEntries01 = new TreeMap<>();

        alphabet01 = new HashSet<>();
        alphabet01.addAll(Arrays.asList('a', 'b'));

        followentry = new FollowPosTableEntry(1,"a");
        followentry.followpos.addAll(Arrays.asList(1,2,3));
        followposTableEntries01.put(1, followentry);

        followentry = new FollowPosTableEntry(2,"b");
        followentry.followpos.addAll(Arrays.asList(1,2,3));
        followposTableEntries01.put(2, followentry);

        followentry = new FollowPosTableEntry(3,"a");
        followentry.followpos.add(4);
        followposTableEntries01.put(3, followentry);

        followentry = new FollowPosTableEntry(4,"b");
        followentry.followpos.add(5);
        followposTableEntries01.put(4, followentry);

        followentry = new FollowPosTableEntry(5,"b");
        followentry.followpos.add(6);
        followposTableEntries01.put(5, followentry);

        followentry = new FollowPosTableEntry(6,"#");
        followposTableEntries01.put(6, followentry);

        stateTransitionTable01 = new HashMap<>();
        DFAState s123 = firstState = new DFAState(1,false, new HashSet<>(Arrays.asList(1,2,3)));
        DFAState s1234 = new DFAState(2,false, new HashSet<>(Arrays.asList(1,2,3,4)));
        DFAState s1235 = new DFAState(3,false, new HashSet<>(Arrays.asList(1,2,3,5)));
        DFAState s1236 = finalState = new DFAState(4,true, new HashSet<>(Arrays.asList(1,2,3,6)));

        Map<Character, DFAState> t123 = new HashMap<>();
        t123.put('a',s1234);
        t123.put('b',s123);

        Map<Character, DFAState> t1234 = new HashMap<>();
        t1234.put('a',s1234);
        t1234.put('b',s1235);

        Map<Character, DFAState> t1235 = new HashMap<>();
        t1235.put('a',s1234);
        t1235.put('b',s1236);

        Map<Character, DFAState> t1236 = new HashMap<>();
        t1236.put('a',s1234);
        t1236.put('b',s123);

        stateTransitionTable01.put(s123,t123);
        stateTransitionTable01.put(s1234,t1234);
        stateTransitionTable01.put(s1235,t1235);
        stateTransitionTable01.put(s1236,t1236);

        genericLexer = new GenericLexer(new DFAGenerator(firstpos_01, followposTableEntries01));
    }

    @Test
    public void correctInput() {
        assertTrue(genericLexer.match("abbbabbabb"));
    }

    @Test
    public void falseInput() {
        assertFalse(genericLexer.match("bbbbbbbb"));
    }

    @Test
    public void emptyInput() {
        assertFalse(genericLexer.match(""));
    }

    @Test
    public void wrongCharacters() {
        assertFalse(genericLexer.match("cdcdcdcd"));
    }
}