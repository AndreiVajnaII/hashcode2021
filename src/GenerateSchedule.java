import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateSchedule {
    public Input input;
    public int nrOfIntersections;
    public HashMap<String, Integer> weights = new HashMap<String, Integer>();

    public GenerateSchedule(Input input) {
        this.input = input;
        this.nrOfIntersections = input.nrOfIntersections;
    }

    public List<List<String>> getInsForIntersections() {
        List<List<String>> result = new ArrayList<List<String>>();
        for (int i = 0; i < input.nrOfIntersections; i++) {
            result.add(new ArrayList<String>());
        }
        
        for (Street s : input.streets) {
            weights.put(s.name, s.duration);
            result.get(s.end).add(s.name);
        }
        return result;
    }

    public Output getStreetsSchedule() {
        Output out = new Output();

        List<List<String>> insForIntersection = getInsForIntersections();
        for (int i = 0; i < insForIntersection.size(); i++) {
            List<String> streetsInIntersection = insForIntersection.get(0);
            List<StreetSchedule> schedules = new ArrayList<StreetSchedule>();
            if (streetsInIntersection.size() == 1) {
                //only one in street so light is always green
                schedules.add(new StreetSchedule(streetsInIntersection.get(0), 1));
            } else {
                schedules = getBestScheduleSolution(streetsInIntersection);
            }
            out.schedules.put(i, schedules);
        }

        return out;
    }

    public List<StreetSchedule> getBestScheduleSolution(List<String> streetsInIntersection) {
        List<StreetSchedule> schedules = new ArrayList<StreetSchedule>();
        for (String s : streetsInIntersection) {
            StreetSchedule schedule = new StreetSchedule(s, weights.get(s));
            schedules.add(schedule);
        }

        return schedules;
    } 
    
}
