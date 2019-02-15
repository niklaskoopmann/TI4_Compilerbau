import DFAGeneration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*

Author 9032365
 */

public class DFAGeneratorTest {

    private DFAGenerator dfaGen;
    private SortedMap <Integer, FollowPosTableEntry> followPosTabEntries;


    @BeforeEach
    void setUp() {
        followPosTabEntries = new TreeMap<>();
        dfaGen = new DFAGenerator();
        Set<Integer> testSet = new HashSet<Integer>();
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);
        FollowPosTableEntry tmpFolPos1 = new FollowPosTableEntry(1,"a");
        tmpFolPos1.followpos.addAll(testSet);
        followPosTabEntries.put(1,tmpFolPos1);
        FollowPosTableEntry tmpFolPos2 = new FollowPosTableEntry(2,"b");
        tmpFolPos2.followpos.addAll(testSet);
        followPosTabEntries.put(2,tmpFolPos2);
        FollowPosTableEntry tmpFolPos3 = new FollowPosTableEntry(3,"a");
        tmpFolPos3.followpos.add(4);
        followPosTabEntries.put(3,tmpFolPos3);
        FollowPosTableEntry tmpFolPos4 = new FollowPosTableEntry(3,"b");
        tmpFolPos4.followpos.add(5);
        followPosTabEntries.put(4,tmpFolPos4);
        FollowPosTableEntry tmpFolPos5 = new FollowPosTableEntry(3,"b");
        tmpFolPos5.followpos.add(6);
        followPosTabEntries.put(5,tmpFolPos5);
        FollowPosTableEntry tmpFolPos6 = new FollowPosTableEntry(3,null);
        followPosTabEntries.put(6,tmpFolPos6);

    }

    @Test
    void generateAlphabetTest(){
        SortedSet<String> alphabet = new TreeSet<>();
        alphabet.add("a");
        alphabet.add("b");
        dfaGen.generateAlphabet(followPosTabEntries);
        assertEquals(dfaGen.getAlphabet(), alphabet);
    }

    @Test
    void generateTransitionMatrixTest (){
        dfaGen.generateTransitionMatrix(followPosTabEntries);
    }

}
