package DFAGeneration;

import java.util.Set;

/**
 * @author Student_3
 */

public class DFAState implements Comparable<DFAState> {

    public final int index;
    public boolean isAcceptingState;
    public final Set<Integer> positionsSet;
    public boolean isInitialState;
    public boolean isMarked;
    public static int indexCounter = 0;

    public DFAState(int index, boolean isAcceptingState, Set<Integer> positionsSet) {
        this.index = index;
        this.isAcceptingState = isAcceptingState;
        this.positionsSet = positionsSet;
        this.isInitialState = false;
        this.isMarked = false;
    }

    public DFAState(boolean isAcceptingState, Set<Integer> positionsSet) {
        this.index = indexCounter;
        this.isAcceptingState = isAcceptingState;
        this.positionsSet = positionsSet;
        this.isInitialState = false;
        this.isMarked = false;
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
        return this.positionsSet == null ? 0 : this.positionsSet.hashCode();
    }

    @Override
    public int compareTo(DFAState other) {
        return (this.index - other.index);
    }

    private static boolean equals(Object o1, Object o2) {
        if (o1 == o2) return true;
        if (o1 == null) return false;
        if (o2 == null) return false;
        return o1.equals(o2);
    }
}
