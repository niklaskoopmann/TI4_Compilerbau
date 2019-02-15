import DFAGeneration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*

Author Simon Schwab
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
        FollowPosTableEntry tmpFolPos4 = new FollowPosTableEntry(4,"b");
        tmpFolPos4.followpos.add(5);
        followPosTabEntries.put(4,tmpFolPos4);
        FollowPosTableEntry tmpFolPos5 = new FollowPosTableEntry(5,"b");
        tmpFolPos5.followpos.add(6);
        followPosTabEntries.put(5,tmpFolPos5);
        FollowPosTableEntry tmpFolPos6 = new FollowPosTableEntry(6,null);
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
        Map<DFAState, DFAState[]> tmpTransitionMatrix;
        tmpTransitionMatrix = new HashMap<>();


        //Erstellen aller States
        Set<Integer> TestS1HashSet = new HashSet<>();
        Set<Integer> TestS1Set;
        TestS1HashSet.add(1);
        TestS1HashSet.add(2);
        TestS1HashSet.add(3);
        TestS1Set=TestS1HashSet;
        DFAState TestState1 = new DFAState(1,false,TestS1Set);
        TestState1.isInitialState=true;

        Set<Integer> TestS2HashSet = new HashSet<>();
        Set<Integer> TestS2Set;
        TestS2HashSet.add(1);
        TestS2HashSet.add(2);
        TestS2HashSet.add(3);
        TestS2Set=TestS2HashSet;
        DFAState TestState2 = new DFAState(2,false,TestS2Set);

        Set<Integer> TestS3HashSet = new HashSet<>();
        Set<Integer> TestS3Set;
        TestS3HashSet.add(4);
        TestS3Set=TestS3HashSet;
        DFAState TestState3 = new DFAState(3,false,TestS3Set);

        Set<Integer> TestS4HashSet = new HashSet<>();
        Set<Integer> TestS4Set;
        TestS4HashSet.add(5);
        TestS4Set=TestS4HashSet;
        DFAState TestState4 = new DFAState(4,false,TestS4Set);

        Set<Integer> TestS5HashSet = new HashSet<>();
        Set<Integer> TestS5Set;
        TestS5HashSet.add(6);
        TestS5Set=TestS5HashSet;
        DFAState TestState5 = new DFAState(5,false,TestS5Set);

        Set<Integer> TestS6HashSet = new HashSet<>();
        Set<Integer> TestS6Set;
        TestS6Set=TestS6HashSet;
        DFAState TestState6 = new DFAState(6,true,TestS6Set);

        //Erstellen der State Arrays als followingStates für jeden State
        DFAState[] parsingArray1 = new DFAState[3];
        parsingArray1[0] = TestState1;
        parsingArray1[1] = TestState2;
        parsingArray1[2] = TestState3;
        tmpTransitionMatrix.put(TestState1,parsingArray1);

        DFAState[] parsingArray2 = new DFAState[3];
        parsingArray2[0] = TestState1;
        parsingArray2[1] = TestState2;
        parsingArray2[2] = TestState3;
        tmpTransitionMatrix.put(TestState2,parsingArray2);

        DFAState[] parsingArray3 = new DFAState[1];
        parsingArray3[0] = TestState4;
        tmpTransitionMatrix.put(TestState3,parsingArray3);

        DFAState[] parsingArray4 = new DFAState[1];
        parsingArray4[0] = TestState5;
        tmpTransitionMatrix.put(TestState4,parsingArray4);

        DFAState[] parsingArray5 = new DFAState[1];
        parsingArray5[0] = TestState6;
        tmpTransitionMatrix.put(TestState5,parsingArray5);

        DFAState[] parsingArray6 = new DFAState[0];
        tmpTransitionMatrix.put(TestState6,parsingArray6);

        if (tmpTransitionMatrix == dfaGen.getTransitionMatrix()){
            assertTrue(true);
        }
    }

}
