package DFAGeneration;

import java.util.Set;

public class DFAState implements Comparable<DFAState> {

    public final int index;
    public Boolean isAcceptingState;
    public final Set<Integer> positionsSet;
    public Boolean isInitialState;
    public Boolean isMarked;
    public static int indexCounter = 0;

    public DFAState(int index, boolean isAcceptingState, Set<Integer> positionsSet) {
        this.index = index;
        this.isAcceptingState = isAcceptingState;
        this.positionsSet = positionsSet;
        this.isInitialState = false;
        this.isMarked=false;
    }

    public DFAState(boolean isAcceptingState, Set<Integer> positionsSet) {
        this.index = indexCounter;
        this.isAcceptingState = isAcceptingState;
        this.positionsSet = positionsSet;
        this.isInitialState = false;
        this.isMarked=false;
        indexCounter++;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            if (obj == null)
                if (getClass() != obj.getClass()) return false;
        DFAState other = (DFAState) obj;
        return (other.index == this.index);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.index;
        return result;
    }

    @Override
    public int compareTo(DFAState other) {
        return (this.index - other.index);
    }
}
