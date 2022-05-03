package DFAGeneration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Student_1
 */

public class FollowPosTableEntry {

    public final int position;
    public final String symbol;
    public final Set<Integer> followpos = new HashSet<>();

    public FollowPosTableEntry(int position, String symbol) {
        this.position = position;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (! (obj instanceof FollowPosTableEntry))
        {
            return false;
        }
        FollowPosTableEntry other = (FollowPosTableEntry) obj;
        return this.position == other.position &&
                this.symbol.equals(other.symbol) &&
                this.followpos.equals(other.followpos);
    }
    @Override
    public int hashCode()
    {
        int hashCode = this.position;
        hashCode = 31 * hashCode + this.symbol.hashCode();
        hashCode = 31 * hashCode + this.followpos.hashCode();
        return hashCode;
    }
}
