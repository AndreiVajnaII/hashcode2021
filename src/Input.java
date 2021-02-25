public class Input {
    public int simuationTime;
    public int points;
    public Street[] streets;
    public String[][] paths;

    public Input(int simulationTime, int points, Street[] streets, String[][] paths) {
        this.simuationTime = simulationTime;
        this.points = points;
        this.streets = streets;
        this.paths = paths;
    }
}
