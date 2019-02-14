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

    public void generateAlphabet (SortedMap<Integer, FollowPosTableEntry> followPosTableEntries){
        //alle followPosTableEntries durchgehen, wenn FollowPosTableEntry.symbol schon
        //existiert weiter, wenn nicht zu alphabet adden
        for (Map.Entry<Integer, FollowPosTableEntry> entry: followPosTableEntries.entrySet()){
            boolean stringExists = false;
            for (String referenceString:this.alphabet){
                if (entry.getValue().symbol == referenceString){
                    stringExists = true;
                    break;
                }
            }
            if (!stringExists){
                this.alphabet.add(entry.getValue().symbol);
            }
        }
    }

    public void generateTransitionMatrix(SortedMap<Integer, FollowPosTableEntry> followPosTableEntries) {
        for (int i; i <= followPosTableEntries.lastKey();i++) {
            //jeder entry mit dem key ist der gleiche Zustand, also ein State objekt,
            //jeder Followpos wird als folgezustand erstellt
            for (Map.Entry<Integer, FollowPosTableEntry> entry: followPosTableEntries.entrySet()) {
                ArrayList<DFAState> StateList = new ArrayList<>();
                int value = entry.getValue().position;
                boolean isAcceptingState = false;
                if (entry.getValue().followpos == null){
                    isAcceptingState = true;
                }
                Set<Integer> positionSet = entry.getValue().followpos;
                DFAState tmpState = new DFAState(value,isAcceptingState,positionSet);
                for (Map.Entry<Integer, FollowPosTableEntry> followEntry: followPosTableEntries.entrySet()){
                    for ( int followposInt : entry.getValue().followpos){
                        if (followEntry.getKey()==followposInt){
                            int followValue = followEntry.getValue().position;
                            boolean followIsAcceptingState = false;
                            if (followEntry.getValue().followpos == null){
                                followIsAcceptingState = true;
                            }
                            Set<Integer> followPositionSet = entry.getValue().followpos;
                            DFAState followState = new DFAState(followValue,followIsAcceptingState,followPositionSet);
                            StateList.add(followState);
                        }
                    }
                }
                DFAState[] StateArray = (DFAState[]) StateList.toArray();
                transitionMatrix.put(tmpState,StateArray);
            }
        }
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
