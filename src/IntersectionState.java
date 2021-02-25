public class IntersectionState {
    public int streetIndex;
    public int remaining;

    public IntersectionState(int streetIndex, int remaining) {
        this.streetIndex = streetIndex;
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "IntersectionState [remaining=" + remaining + ", streetIndex=" + streetIndex + "]";
    }
}
