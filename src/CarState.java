public class CarState {
    public int carIndex;
    public int remaining;
    public int streetIndex;

    public CarState(int carIndex, int remaining, int streetIndex) {
        this.carIndex = carIndex;
        this.remaining = remaining;
        this.streetIndex = streetIndex;
    }

    @Override
    public String toString() {
        return "CarState [carIndex=" + carIndex + ", remaining=" + remaining + ", streetIndex=" + streetIndex + "]";
    }
}
