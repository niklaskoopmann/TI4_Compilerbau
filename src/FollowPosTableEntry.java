import java.util.HashSet;
import java.util.Set;

public class FollowPosTableEntry {

    public final int position;
    public final String symbol;
    public final Set<Integer> followpos = new HashSet<>();

    public FollowPosTableEntry(int position, String symbol) {
        this.position = position;
        this.symbol = symbol;
    }
}
