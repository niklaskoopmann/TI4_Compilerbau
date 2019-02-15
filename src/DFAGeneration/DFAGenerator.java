package DFAGeneration;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.util.*;

/*
Author Simon Schwab
 */

public class DFAGenerator {

    private SortedSet<String> alphabet;
    private DFAState initialState;
    private Map<DFAState, DFAState[]> transitionMatrix;

    public DFAGenerator() {
        this.alphabet = new TreeSet<String>();
        this.transitionMatrix = new HashMap<DFAState, DFAState[]>();
    }

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

    //Funktion zum Erstellen der transition-matrix
    //Die Funktion erstellt aus der übergebenen Tabelle die Einzelnen States und setzt schließlich diese zur Matrix zusammen
    public void generateTransitionMatrix (SortedMap<Integer, FollowPosTableEntry> followPosTableEntries){
        //Zuerst werden alle möglichen States erstellt
        boolean isFirstState = true;
        ArrayList<DFAState> allUniqueStates = new ArrayList<>();
        for (Map.Entry<Integer, FollowPosTableEntry> entry: followPosTableEntries.entrySet()){
            int value = entry.getValue().position;
            boolean isAcceptingState = false;
            if (entry.getValue().followpos.size() == 0){
                isAcceptingState = true;
            }
            Set<Integer> positionSet = entry.getValue().followpos;
            DFAState tmpState = new DFAState(value,isAcceptingState,positionSet);
            if (isFirstState){
                tmpState.isInitialState=true;
                this.initialState = tmpState;
                isFirstState = false;
            }
            allUniqueStates.add(tmpState);
        }

        //Für jeden State werden nun die following States geschrieben
        for (DFAState aDFAState : allUniqueStates){
            ArrayList<DFAState> StateList = new ArrayList<>();
            for (int i :aDFAState.positionsSet){
                for(DFAState followingState : allUniqueStates){
                    if (followingState.index==i){
                        StateList.add(followingState);
                    }
                }
            }
            DFAState[] StateArray = new DFAState[StateList.size()];
            for (int count = 0; count < StateArray.length; count++){
                StateArray[count] = StateList.get(count);
            }
            transitionMatrix.put(aDFAState,StateArray);
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
