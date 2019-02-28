package DFAGeneration;
import java.util.*;

/*
Author Simon Schwab
 */

public class DFAGenerator {

    private SortedSet<String> alphabet;
    private DFAState initialState;
    private SortedMap<DFAState, Map<String, DFAState>> transitionMatrix = new TreeMap<>();

    //Aus den übergebenen Tabelle wird das allgemeine Alphabet für den DEA/DFA erstellt
    //jedes einzigartige Symbol wird in dem TreeSet alphabet abgebildet
    public void generateAlphabet (SortedMap<Integer, FollowPosTableEntry> followPosTableEntries){
        for (Map.Entry<Integer, FollowPosTableEntry> entry: followPosTableEntries.entrySet()){
            boolean stringExists = false;
            for (String referenceString:this.alphabet){
                if (entry.getValue().symbol == referenceString){
                    stringExists = true;
                }
            }
            if ((!stringExists)&&(entry.getValue().symbol!=null)){
                this.alphabet.add(entry.getValue().symbol);
            }
        }
    }


    public void generateTransitionMatrix (SortedMap<Integer, FollowPosTableEntry> followPosTableEntries){
        //initialize Dstates only with firstpos
        DFAState initialState = new DFAState(false,getFirstEntrySet(followPosTableEntries));
        initialState.isAcceptingState = checkAcceptingState(initialState,followPosTableEntries);
        initialState.isInitialState= true;
        ArrayList<DFAState> Dstates = new ArrayList<>();
        Dstates.add(initialState);
        generateAlphabet(followPosTableEntries);

        //while unmarked state exists in Dstates
        while(checkMarkedDstates(Dstates)){
            //get and mark unmarked State S
            DFAState S = getUnmarkedState(Dstates);
            S.isMarked = true;
            //foreach
            for (String a : this.alphabet){
                //new State U == followpos for a in S
                Set<Integer> SFollowingEntries = new TreeSet<>();
                //TODO initialize U with parameters
                //followpos für U erstellen
                for (int i : S.positionsSet) {
                    for (FollowPosTableEntry entry: followPosTableEntries.values()){
                        if ((entry.position == i) && (entry.symbol == a)) {
                            for (Integer followInt: entry.followpos) {
                                for (Integer SFollowInt : SFollowingEntries) {
                                    if (SFollowInt!=followInt){
                                        SFollowingEntries.add(followInt);
                                    }
                                }
                            }
                        }
                    }
                }
                //U initialisieren
                DFAState U = new DFAState(false, SFollowingEntries);
                U.isAcceptingState = checkAcceptingState(U,followPosTableEntries);

                //if U is not in Dstated add as unmarked (DFAStates are unmarked as default)
                if (checkDStates(U,Dstates)){
                    Dstates.add(U);
                }

                //add to transitionMatrix
                Map<String, DFAState> innerTransitionMap = new HashMap<>();
                innerTransitionMap.put(a,U);
                transitionMatrix.put(S,innerTransitionMap);
            }

        }

    }

    private boolean checkAcceptingState (DFAState state, SortedMap<Integer, FollowPosTableEntry> followPosTableEntries){
        for (FollowPosTableEntry entry: followPosTableEntries.values()){
            for (int i : state.positionsSet){
                if (entry.position == i){
                    if (entry.symbol=="#"){
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean checkDStates(DFAState U,ArrayList<DFAState> Dstates) {
        for (DFAState state:Dstates ) {
            if (state==U){
                return true;
            }
        }
        return false;
    }


    //returns the positionSet of the first Followpostableentry (the initial State)
    private Set<Integer> getFirstEntrySet(SortedMap<Integer,FollowPosTableEntry> followPosTableEntries) {
        for (Map.Entry<Integer, FollowPosTableEntry> entry : followPosTableEntries.entrySet()){
            Set<Integer> positionSet = entry.getValue().followpos;
            return positionSet;
        }
        return null;
    }

    private DFAState getUnmarkedState(ArrayList<DFAState> Dstates) {
        for (DFAState state: Dstates){
            if (state.isMarked ==false){
                return state;
            }
        }
        return null;
    }

    private boolean checkMarkedDstates(ArrayList<DFAState> Dstates) {
        for (DFAState state: Dstates) {
            if (state.isMarked=false){
                return false;
            }
        }
        return true;
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

    public SortedMap<DFAState, Map<String, DFAState>> getTransitionMatrix() {
        return transitionMatrix;
    }

    public void setTransitionMatrix(SortedMap<DFAState, Map<String, DFAState>> transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }
}
